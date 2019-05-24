pipeline {
    agent { label 'slave' }

    parameters {
        string(defaultValue: "deploy-moh-cli-test-amapoc-moh-private-web-4", description: 'Enter Stack Name', name: 'StackName')
        string(defaultValue: "amapocmoh-5", description: 'Enter OS', name: 'HostName')
        string(defaultValue: "rhel", description: 'Enter OS', name: 'OS')
        string(defaultValue: "ami-0d13543d534a74a43", description: 'Enter Image ID', name: 'InstanceAmi')
        string(defaultValue: "subnet-061b3d9a7f8b88210", description: 'Enter Subnet ID', name: 'SubnetId')
        string(defaultValue: "t2.micro", description: 'Enter Instance Type', name: 'InstanceType')
        string(defaultValue: "BTSManagedInstanceProfile", description: 'BTSManagedInstanceProfile', name: 'InstanceProfile')
        string(defaultValue: "DOI-POC-MOHAMMAD-ACCESS-KEY", description: 'Enter EC2 Key', name: 'KeyName')
        string(defaultValue: "sg-0accba28318e4819c", description: 'Enter Security Group', name: 'LinuxSecurityGroup')
        string(defaultValue: "mohammad.ayub@industry.nsw.gov.au", description: 'Enter System Owners email address', name: 'SystemOwner')
        string(defaultValue: "arn:aws:sns:ap-southeast-2:820636345429:mohammad-ping-poc-topic", description: 'Enter OS', name: 'SNSTopicARN')
        string(defaultValue: "mohammad-resize-dest", description: 'Enter Bucket Name', name: 'BucketName')
    }
    stages{
        stage('template copy'){
            steps {
                git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
                sh "cp single_instance_linux.yaml /root/jenkins.aws.templates/single_instance_linux.yaml"
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
                --template-body file:///root/jenkins.aws.templates/single_instance_linux.yaml --parameters\
                ParameterKey=SubnetId,ParameterValue=${params.SubnetId} ParameterKey=InstanceAmi,ParameterValue=${params.InstanceAmi} \
                ParameterKey=SystemOwner,ParameterValue=${params.SystemOwner} ParameterKey=OS,ParameterValue=${params.OS} \
                ParameterKey=HostName,ParameterValue=${params.HostName} ParameterKey=SNSTopicARN,ParameterValue=${params.SNSTopicARN} \
                ParameterKey=InstanceType,ParameterValue=${params.InstanceType} ParameterKey=InstanceProfile,ParameterValue=${params.InstanceProfile} \
                ParameterKey=KeyName,ParameterValue=${params.KeyName} ParameterKey=LinuxSecurityGroup,ParameterValue=${params.LinuxSecurityGroup} \
                " 
        
                archiveArtifacts artifacts: '*'
            }
        }
    }
}