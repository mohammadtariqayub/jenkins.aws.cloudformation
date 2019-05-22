pipeline {
    agent { label 'slave' }

    stages{
        stage('List'){
            steps {
                sh '/root/.local/lib/aws/bin/aws s3 ls'
            }
        }
   }
}
