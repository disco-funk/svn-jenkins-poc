#!/usr/bin/env groovy

def dependencyTree = ["module1": "module-with-dependencies", "module2": "module-with-dependencies"]
def leafTasks = [:]
def dependentTasks = [:]
def changedDirs
Set<String> dependentSet = []

node {
    stage('Checkout from SVN') {
        checkout scm
    }

    stage('Generate Parallel Build Tasks') {
        changedDirs = getChangeSetDirectories()
        print "All changed modules: ${changedDirs}"

        for (String changedDir : changedDirs) {
            leafTasks["Building ${changedDir}"] = {
                stage("Building ${changedDir}") {
                    dir(changedDir) {
                        sh 'make'
                    }
                }
            }
        }
    }

    parallel leafTasks

    stage('Checking Dependent Modules') {
        print "All dependent modules: ${dependencyTree}"

        for (Map.Entry<String,String> dependentDir : dependencyTree.entrySet()) {
            print "Checking ${dependentDir.key} dependencies"
            if(changedDirs.contains(dependentDir.key)) {
                dependentSet.add(dependentDir.value)
            }
        }

        if(dependentSet.size() == 0) {
            println "No dependencies to rebuild"
        } else {
            for (String dependent : dependentSet) {
                dependentTasks["Building ${dependent}"] = {
                    stage("Building ${dependent}") {
                        dir(dependent) {
                            sh 'make'
                        }
                    }
                }
            }
        }
    }

    parallel dependentTasks
}

Set<String> getChangeSetDirectories() {
    def changeLogSets = currentBuild.rawBuild.changeSets
    List affectedPaths = []
    Set<String> subdirectories = []

    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j]
            affectedPaths = affectedPaths.plus(entry.getAffectedPaths())
        }
    }

    print "All affected paths in SVN changeSet: ${affectedPaths}"

    for (int i = 0; i < affectedPaths.size(); i++) {
        if (affectedPaths[i].contains("/")) {
            def split = affectedPaths[i].split("/")
            subdirectories.add(split[0])
        }
    }

    return subdirectories
}