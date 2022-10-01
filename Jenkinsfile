pipeline {

    agent {
        kubernetes {
            label 'default'
            defaultContainer 'maven'
            customWorkspace "workspace/${UUID.randomUUID()}"
        }
    }

    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
        timeout(time: 1, unit: 'HOURS')
        skipDefaultCheckout()
    }

    stages {
        stage("Pipeline") {
            steps {
                pipelineStages()
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
 }

 def debugInfo() {
       sh "mvn --version"
       sh "java -version"
 }

 def pipelineStages() {

     stage("Checkout Code") {
         checkout scm
     }

     stage("Building tests") {
        sh "mvn clean install"
     }

     stage("Running tests") {
        sh "docker run -e SELENIUM_HUB_URL=http://selenium-router.selenium-grid.svc.cluster.local:4444 testng-docker:latest"
     }
    try {
    stage("Publish reports") {
        allure([includeProperties: false, reportBuildPolicy: 'ALWAYS', results: [[path: '**/target/allure-results']]])
    }
    } catch(e) {
        e.printStackTrace()
    }
}