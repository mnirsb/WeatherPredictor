pipeline {
    agent any

    options {
        timeout(time: 1, unit: 'HOURS')
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    environment {
        mavenHome = tool 'maven'
    }

    tools {
        jdk 'jdk17'
    }

    stages {
	
	stage('Checkout') {
            steps {
             
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/mnirsb/WeatherPredictor.git']]])
            }
        }

        stage('Build') {
            steps {
                // Change to the project directory
                dir('D:\\Interview-Prep\\WeatherPrediction\\WeatherPrediction') {
                    bat "\"${mavenHome}/bin/mvn\" clean install -DskipTests"
                }
            }
        }

        stage('Test') {
            steps {
                dir('D:\\Interview-Prep\\WeatherPrediction\\WeatherPrediction') {
                    bat "\"${mavenHome}/bin/mvn\" test"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Add deployment steps here
                    echo "Deploy: Please provide deployment provisions"
                }
            }
        }
    }

    post {
        success {
            // Steps to execute if the pipeline is successful
            echo "Pipeline succeeded!"
        }

        failure {
            // Steps to execute if the pipeline fails
            echo "Pipeline failed!"
        }
    }
}
