#!/usr/bin/env groovy

def tasks = [:]

node {
    stage('Checkout') {
        checkout scm
    }

    stage('Generate Tasks') {
        def changedDirs = getChangeSetDirectories()
        print "All changed modules: ${changedDirs}"

        for (String changedDir : changedDirs) {
            tasks["Building ${changedDir}"] = {
                stage("Building ${changedDir}") {
                    dir(changedDir) {
                        sh 'make'
                    }
                }
            }
        }
    }

    parallel tasks
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