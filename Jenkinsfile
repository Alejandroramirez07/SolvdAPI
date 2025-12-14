pipeline {
    agent any
    tools {
        jdk 'jdk21'
        maven 'maven'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Run API Tests') {
            steps {
                bat 'mvn clean test -Papi-only'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
        stage('Run Docker Tests') {
            steps {
                bat 'docker-compose up --build test-runner'
            }
        }
        stage('Cleanup') {
            steps {
                bat 'docker-compose down'
                bat 'docker system prune -f'
            }
        }
    }
}