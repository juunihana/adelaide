pipeline {
  agent any
  environment {
      dockerContainerName = 'adelaide'
      dockerImageName = 'adelaide-api'
  }

  tools {
    jdk 'JDK22'
    maven 'MVN3'
  }

  stages {
    stage ('build') {
      steps {
        withMaven {
          sh 'mvn clean package -DskipTests=true'
        }
      }
    }

    stage('deploy') {
      steps {
        sh 'docker ps -f name=${dockerContainerName} -q | xargs --no-run-if-empty docker container stop'
        sh 'docker container ls -a -fname=${dockerContainerName} -q | xargs -r docker container rm'
        sh 'docker images -q --filter=reference=${dockerImageName} | xargs --no-run-if-empty docker rmi -f'
        sh 'docker compose up -d --build'
      }
    }
  }
}