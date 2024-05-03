pipeline {
  agent any
  environment {
      registry = ""
      dockerContainerName = 'adelaide'
      dockerImageName = 'adelaide-api'
  }

  tools {
    jdk "JDK22"
  }

  stages {
    stage ('build') {
      steps {
        withMaven (maven: "MAVEN_ENV") {
          sh "mvn clean package -DskipTests=true"
        }
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
        sh 'docker compose up -d --build'
      }
    }
  }
}