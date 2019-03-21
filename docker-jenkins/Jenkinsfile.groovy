#!groovy

pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[cancelProcessOnExternalsFail: true, credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: 'svn://svn-server/example-repo']], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])
            }
        }
        stage('Build') {
            parallel {
                stage('Make Module 1') {
                    steps {
                        dir("module1") {
                            sh 'make'
                        }
                    }
                }
                stage('Make Module 2') {
                    steps {
                        dir("module2") {
                            sh 'make'
                        }
                    }
                }
            }
        }
    }
}