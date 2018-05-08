pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '''wget https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
java -jar BuildTools.jar'''
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