#!groovy
def tasks = [:]

node {
    stage('Checkout') {
        checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[cancelProcessOnExternalsFail: true, credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: 'svn://svn-server/example-repo']], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])
    }

    stage('Generate tasks') {
        def changedDirs = getChangeSetDirectories()
            for (String changedDir : changedDirs) {
                tasks[changedDir] = {
                stage ("Building" + changedDir) {
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

    for (int i = 0; i < affectedPaths.size(); i++) {
        def split = affectedPaths[i].split("/")
        subdirectories.add(split[1])
    }

    return subdirectories
}