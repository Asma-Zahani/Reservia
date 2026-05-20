pipeline {
    agent any

    stages {

        stage('Clone OK') {
            steps {
                echo 'Projet récupéré depuis Gitea'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
