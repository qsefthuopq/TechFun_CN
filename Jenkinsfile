pipeline {
  agent {
    docker {
      image 'maven'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean package'
      }
    }
    stage('Archive Artifacts') {
      steps {
        archiveArtifacts 'target/*.jar'
      }
    }
  }
}