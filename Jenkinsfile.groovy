pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/christoust/jenkins-script.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Deploy to Development') {
            when {
                branch 'development'
            }
            steps {
                sh 'mvn deploy -Denvironment=development'
            }
        }

        stage('Deploy to QA') {
            when {
                branch 'qa'
            }
            steps {
                sh 'mvn deploy -Denvironment=qa'
            }
        }

        stage('Deploy to Production') {
            when {
                branch 'master'
            }
            steps {
                sh 'mvn deploy -Denvironment=production'
            }
        }
    }
}
