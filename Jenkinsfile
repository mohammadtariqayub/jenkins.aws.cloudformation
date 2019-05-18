pipeline {
    agent { label 'slave' }
    
    stages{
        stage('List'){
            steps {
                sh 'PATH=/root/.local/lib/aws/bin:$PATH:$HOME/bin; export $PATH'
                sh '/root/.local/lib/aws/bin/aws s3 ls'
            }
        }
   }
}
