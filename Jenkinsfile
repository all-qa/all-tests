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

def pipelineStages() {

     stage("Checkout e2e tests") {
         checkout scm
     }

     stage("Running e2e tests") {
        sh "mvn clean verify -Dselenium.hub.url=http://selenium-router.selenium-grid.svc.cluster.local:4444 -Dselenium.browser=chrome -Dselenium.target.url=https://google.com"
     }

    try {
    stage("Publishing e2e reports") {
        sh 'curl -o allure-2.19.0.tgz -OLs https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.19.0/allure-commandline-2.19.0.tgz'
        sh 'tar -zxvf allure-2.19.0.tgz -C /opt/'
        sh 'ln -s /opt/allure-2.19.0/bin/allure /usr/bin/allure'
        allure([includeProperties: false, reportBuildPolicy: 'ALWAYS', results: [[path: '**/target/allure-results']]])
    }
    } catch(e) {
        e.printStackTrace()
        echo "Cannot publish allure reports"
    }
}