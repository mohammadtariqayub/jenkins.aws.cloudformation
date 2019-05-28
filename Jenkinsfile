pipeline {
    agent { label 'slave' }

    parameters {
        string(defaultValue: "deploy-moh-cli-test-amapoc-moh-private-web-1", description: 'Enter Stack Name', name: 'StackName')
        string(defaultValue: "amapocmoh-1", description: 'Enter Host Name', name: 'HostName')
        string(defaultValue: "windows", description: 'Enter OS', name: 'OS')
        string(defaultValue: "ami-0628ef1f10e34307d", description: 'Enter Image ID', name: 'ImageID')
        string(defaultValue: "subnet-061b3d9a7f8b88210", description: 'Enter Subnet ID', name: 'SubnetId')
        string(defaultValue: "t2.small", description: 'Enter Instance Type', name: 'InstanceType')
        string(defaultValue: "poc", description: 'Environment type. eg, prd', name: 'Environment')
        string(defaultValue: "true", description: 'Specifiy is the instance should join  Active Directory domain.', name: 'JoinDomain')
        string(defaultValue: "businesshours", description: 'A valid power schedule. eg, 247', name: 'GorillaStack')
        string(defaultValue: "aws-disrd-poc", description: 'Name of the AWS Account associated with this instance', name: 'AWSAccount')
        string(defaultValue: "BTSManagedInstanceProfile", description: 'BTSManagedInstanceProfile', name: 'InstanceProfile')
        string(defaultValue: "DOI-POC-MOHAMMAD-ACCESS-KEY", description: 'Enter EC2 Key', name: 'KeyPairName')
        string(defaultValue: "sg-0accba28318e4819c", description: 'Enter Security Group', name: 'SecurityGroupId')
        string(defaultValue: "arn:aws:sns:ap-southeast-2:820636345429:mohammad-ping-poc-topic", description: 'Enter the SNS Topic ARN to subscribe to', name: 'SNSTopicARN')
        string(defaultValue: "mohammad.ayub@industry.nsw.gov.au", description: 'Enter System Owners email address', name: 'SystemOwner')        
        string(defaultValue: "mohammad-resize-dest", description: 'Enter Bucket Name', name: 'BucketName')
    }
    stages{
        stage('template copy'){
            steps {
                git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
                sh "cp single_instance_windows-cutomized.yaml /root/jenkins.aws.templates/single_instance_windows-cutomized.yaml"
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
                --template-body file:///root/jenkins.aws.templates/single_instance_windows-cutomized.yaml --parameters\
                ParameterKey=SubnetId,ParameterValue=${params.SubnetId} ParameterKey=ImageID,ParameterValue=${params.ImageID} \
                ParameterKey=SystemOwner,ParameterValue=${params.SystemOwner} ParameterKey=OS,ParameterValue=${params.OS} \
                ParameterKey=HostName,ParameterValue=${params.HostName} ParameterKey=SecurityGroupId,ParameterValue=${params.SecurityGroupId} \
                ParameterKey=InstanceType,ParameterValue=${params.InstanceType} ParameterKey=InstanceProfile,ParameterValue=${params.InstanceProfile} \
                ParameterKey=KeyPairName,ParameterValue=${params.KeyPairName} ParameterKey=Environment,ParameterValue=${params.Environment} \
                ParameterKey=AWSAccount,ParameterValue=${params.AWSAccount} ParameterKey=GorillaStack,ParameterValue=${params.GorillaStack} \
                ParameterKey=SNSTopicARN,ParameterValue=${params.SNSTopicARN} ParameterKey=JoinDomain,ParameterValue=${params.JoinDomain}" 
        
                archiveArtifacts artifacts: '*'
            }
        }
    }
}