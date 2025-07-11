pipeline {
    agent any

    tools {
        maven 'Maven 3.9.0'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/arvalinno/selenium-testng.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
    }
}
