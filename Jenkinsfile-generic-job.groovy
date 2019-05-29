pipeline {
    agent { label 'slave' }

    parameters {
        string(defaultValue: "subnet-061b3d9a7f8b88210", description: 'Enter Subnet ID', name: 'SubnetId')
        string(defaultValue: "ami-0fb7513bcdc525c3b", description: 'Enter Image ID', name: 'ImageId')
        string(defaultValue: "mohammad-resize-dest", description: 'Enter Bucket Name', name: 'BucketName')
    }
    stages{
        stage('template copy'){
            steps {
                git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
                sh "cp Jenkinsfile-windows.groovy /root/jenkins.aws.templates/Jenkinsfile-windows.groovy"
            }
        }
        stage('list s3 bucket'){
            steps {
                echo "Bucket Name: ${params.BucketName}"
                sh "/root/.local/lib/aws/bin/aws s3 ls ${params.BucketName}"
            }
        }
        stage('ec2 creation'){
        echo "Deploying ec2 to POC VPC"
        build job: 'AWSEc2-windows-creation-CD',
              parameters: [
                string(defaultValue: "deploy-moh-cli-test-amapoc-moh-private-web-3", description: 'Enter Stack Name', name: 'StackName')
                string(defaultValue: "subnet-061b3d9a7f8b88210", description: 'Enter Subnet ID', name: 'SubnetId')
                string(defaultValue: "sg-0accba28318e4819c", description: 'Enter Security Group', name: 'SecurityGroupIds')
                string(defaultValue: "ami-0628ef1f10e34307d", description: 'Enter Image ID', name: 'ImageId')
                string(defaultValue: "mohammad.ayub@industry.nsw.gov.au", description: 'Enter System Owners email address', name: 'SystemOwner')
                string(defaultValue: "windows", description: 'Enter OS', name: 'OS')
                string(defaultValue: "amapocmoh-4", description: 'Enter Host Name', name: 'HostName')
                string(defaultValue: "arn:aws:sns:ap-southeast-2:820636345429:mohammad-ping-poc-topic", description: 'Enter SNS Topics', name: 'SNSTopicARN')
                string(defaultValue: "mohammad-resize-dest", description: 'Enter Bucket Name', name: 'BucketName')
          ]
        }
    }
}
