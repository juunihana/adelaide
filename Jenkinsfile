pipeline {
  agent any
  environment {
      registry = ""
      dockerContainerName = 'adelaide'
      dockerImageName = 'adelaide-api'
  }

  tools {
    maven 'Maven_3.9.6'
  }

  stages {
    stage ('build') {
      steps {
          sh "mvn clean package"
      }
    }

    stage('clean container') {
      steps {
        sh 'docker ps -f name=${dockerContainerName} -q | xargs --no-run-if-empty docker container stop'
        sh 'docker container ls -a -fname=${dockerContainerName} -q | xargs -r docker container rm'
        sh 'docker images -q --filter=reference=${dockerImageName} | xargs --no-run-if-empty docker rmi -f'
      }
    }

    stage('docker-compose start') {
      steps {
        sh 'docker compose up -d'
      }
    }
  }
}