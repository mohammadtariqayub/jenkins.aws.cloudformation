pipeline {
    agent { label 'slave' }

    stages{
        stage('ec2-deploy'){
            steps {
                sh 'cd /root/jenkins.aws.templates/jenkins.aws.cloudformation'
                sh 'git pull'
                sh '/root/.local/lib/aws/bin/aws cloudformation deploy --template-file /root/jenkins.aws.templates/jenkins.aws.cloudformation/single_instance_tariq.yaml --stack-name deploy-moh-cli-test-amapoc-moh-private-web-3'
            }
        }
   }
}
