pipeline {
    agent any
    node (params.slave) {
    stages{
        stage('List'){
            steps {
                sh 'PATH=/root/.local/lib/aws/bin:$PATH:$HOME/bin'
                sh 'aws s3 ls'
            }
        }
   }
  }
}
