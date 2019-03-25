#!/usr/bin/env groovy

def leafTasks = [:]
def dependentTasks = [:]
def changedDirs
def dependencyTree = ["module1": "module-with-dependencies", "module2": "module-with-dependencies"]

node {
    stage('Checkout') {
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
            print "key : ${dependentDir.key}"
            if(changedDirs.contains(dependentDir.key)) {
                dependentTasks["Building ${dependentDir.value}"] = {
                    stage("Building ${dependentDir.value}") {
                        dir(dependentDir.value) {
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