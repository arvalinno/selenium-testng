pipeline {
    agent any

    tools {
        maven 'Maven 3.9.10'
    }

    environment {
        IMAGE_NAME = 'selenium-testng'
        TAG        = 'latest'
    }

    stages {
        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Build JAR') {
            steps {
                // Build the Selenium TestNG artifact
                sh 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                // Build Docker image named selenium-testng:latest
                sh 'docker build -t ${IMAGE_NAME}:${TAG} .'
            }
        }

        stage('Deploy Container') {
            steps {
                // Stop and remove any existing container, then run new one
                sh '''
                docker stop ${IMAGE_NAME} || true
                docker rm ${IMAGE_NAME}   || true
                docker run -d \
                    --name ${IMAGE_NAME} \
                    -p 8081:8080 \
                    ${IMAGE_NAME}:${TAG}
                '''
            }
        }

    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'

            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/surefire-reports',
                reportFiles: 'emailable-report.html',
                reportName: 'TestNG Report'
            ])

            echo "Pipeline completed"
        }
    }
}
