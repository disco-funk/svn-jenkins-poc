#!groovy

node {
    stage('Checkout') {
        checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[cancelProcessOnExternalsFail: true, credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: 'svn://svn-server/example-repo']], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])
    }
    stage('Getlist of changes') {
        print getModules()

        print getChangeSetDirectories()
    }
}

List<String> getChangeSetDirectories() {
    def changeLogSets = currentBuild.rawBuild.changeSets
    List affectedPaths = []
    List subdirectories = []

    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j]
            affectedPaths = affectedPaths.plus(entry.getAffectedPaths())
        }
    }

    for (int i = 0; i < affectedPaths.size(); i++) {
        def split = affectedPaths[i].split("/")
        subdirectories.push(split[1])
    }

    return subdirectories
}

List<String> getModules() {
    return sh(
            script: 'ls -d *',
            returnStdout: true
    ).split("\n").findAll { it.indexOf('@') == -1 }
}