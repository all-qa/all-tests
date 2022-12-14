pipeline {

    agent {
        kubernetes {
            label 'default'
            defaultContainer 'maven'
            customWorkspace "workspace/${UUID.randomUUID()}"
        }
    }

    parameters {
        choice(description: 'Target browser', choices: ['chrome', 'firefox', 'edge'], name: 'BROWSER')
    }

    environment {
        MAVEN_OPTS = " -Xms512m -Xmx1024m"
        PATH="$PATH:/usr/local/mvnd/bin/"
        JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64/"
    }

    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
        timeout(time: 1, unit: 'HOURS')
        skipDefaultCheckout()
        ansiColor('xterm')
    }

    stages {
        stage("e2e pipeline") {
            steps {
                e2ePipelineStages()
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
 }

def e2ePipelineStages() {

     stage("Checkout e2e tests") {
         checkout scm
     }

     stage("Running e2e tests mvnd") {
        //withAllureUpload(serverId: 'allure-testops', projectId: '1', results: [[path: 'target/allure-results']], , tags: "${params.BROWSER}") {
        sh 'mvnd --status'
        sh "mvnd -V -Dmvnd.daemonStorage=/usr/local/mvnd/cache clean verify -Dselenium.hub.url=http://selenium-router.selenium-grid.svc.cluster.local:4444 -Dselenium.browser=${params.BROWSER} -Dselenium.target.url=https://google.com"
        sh 'mvnd --status'
        //}
     }

//      stage("Running e2e tests mvn") {
//         sh "mvn clean verify -Dselenium.hub.url=http://selenium-router.selenium-grid.svc.cluster.local:4444 -Dselenium.browser=${params.BROWSER} -Dselenium.target.url=https://google.com"
//      }

}
