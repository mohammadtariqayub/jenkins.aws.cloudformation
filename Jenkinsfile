pipeline {
    agent { label 'slave' }

    stages{
        stage('ec2-deploy'){
            steps {
                sh '/root/.local/lib/aws/bin/aws cloudformation deploy --template-file /root/single_instance_tariq.yaml --stack-name deploy-moh-cli-test-amapoc-moh-private-web-3'
            }
        }
   }
}
