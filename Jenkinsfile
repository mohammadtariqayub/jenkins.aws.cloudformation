pipeline {
    agent any
    
    stages{
        stage('List'){
            steps {
                sh 'PATH=/root/.local/lib/aws/bin:$PATH:$HOME/bin'
                sh 'aws s3 ls'
            }
        }
   }
}
