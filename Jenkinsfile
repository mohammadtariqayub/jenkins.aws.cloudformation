pipeline {
    agent { label 'slave' }

    stages{
        stage('template copy'){
            steps {
                git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
                sh "cp single_instance_tariq.yaml /root/jenkins.aws.templates/single_instance_tariq.yaml"
                archiveArtifacts artifacts: '*.txt'
            }
        }
        stage('ec2-deploy'){
            steps {
                sh '/root/.local/lib/aws/bin/aws cloudformation create-stack --stack-name deploy-moh-cli-test-amapoc-moh-private-web-3 --template-body file:///root/jenkins.aws.templates/single_instance_tariq.yaml --parameters ParameterKey=SubnetId,ParameterValue=subnet-061b3d9a7f8b88210 ParameterKey=ImageId,ParameterValue=ami-05067171f4230ac41'
            }
        }
   }
}
