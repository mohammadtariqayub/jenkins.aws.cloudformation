pipeline {
    agent { label 'slave' }

    parameters {
        string(defaultValue: "deploy-moh-cli-test-amapoc-moh-private-web-3", description: 'Enter Stack Name', name: 'StackName')
        string(defaultValue: "subnet-061b3d9a7f8b88210", description: 'Enter Subnet ID', name: 'SubnetId')
        string(defaultValue: "sg-0accba28318e4819c", description: 'Enter Security Group', name: 'SecurityGroupIds')
        string(defaultValue: "ami-0628ef1f10e34307d", description: 'Enter Image ID', name: 'ImageId')
        string(defaultValue: "mohammad.ayub@industry.nsw.gov.au", description: 'Enter System Owners email address', name: 'SystemOwner')
        string(defaultValue: "windows", description: 'Enter OS', name: 'OS')
        string(defaultValue: "amapocmoh-4", description: 'Enter Host Name', name: 'HostName')
        string(defaultValue: "arn:aws:sns:ap-southeast-2:820636345429:mohammad-ping-poc-topic", description: 'Enter SNS Topics', name: 'SNSTopicARN')
        string(defaultValue: "mohammad-resize-dest", description: 'Enter Bucket Name', name: 'BucketName')
    }
    stages{
        stage('template copy'){
            steps {
                git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
                sh "cp single_instance_generic.yaml /root/jenkins.aws.templates/single_instance_generic.yaml"
            }
        }
        stage('list s3 bucket'){
            steps {
                echo "Bucket Name: ${params.BucketName}"
                sh "/root/.local/lib/aws/bin/aws s3 ls ${params.BucketName}"
            }
        }
        stage('ec2-deploy'){
            steps {
                sh "/root/.local/lib/aws/bin/aws cloudformation create-stack --stack-name ${params.StackName}\
                --template-body file:///root/jenkins.aws.templates/single_instance_generic.yaml --parameters\
                ParameterKey=SubnetId,ParameterValue=${params.SubnetId} ParameterKey=ImageId,ParameterValue=${params.ImageId} \
                ParameterKey=SystemOwner,ParameterValue=${params.SystemOwner} ParameterKey=OS,ParameterValue=${params.OS} \
                ParameterKey=HostName,ParameterValue=${params.HostName} ParameterKey=SNSTopicARN,ParameterValue=${params.SNSTopicARN} \
                ParameterKey=SecurityGroupIds,ParameterValue=${params.SecurityGroupIds}" 
        
                archiveArtifacts artifacts: '*'
            }
        }
    }
}