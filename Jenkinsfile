pipeline {
    agent any

    tools {
        maven 'Maven 3.9.10'
    }

    stages {
        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Deploy - Option 1') {
            steps {
                echo 'Running Spring Boot inside Jenkins container'
                sh 'pkill -f your-app.jar || true' // cleanup from previous builds
                sh 'nohup java -jar target/your-app.jar > app.log 2>&1 &'
            }
        }

        stage('Results') {
            steps {
                echo 'Publishing test results...'
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
        }
    }
}
