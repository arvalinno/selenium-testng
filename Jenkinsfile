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

        stage('Results') {
            steps {
                post {
                    always {
                        junit '**/target/surefire-reports/*.xml' // optional for test tab

                        publishHTML([
                            reportDir: 'target/surefire-reports',
                            reportFiles: 'emailable-report.html',
                            reportName: 'TestNG Report'
                        ])
                    }
                }
            }
        }
    }
}


