pipeline {
    agent { label 'slave' }

    parameters {
        string(defaultValue: "subnet-061b3d9a7f8b88210", description: 'Enter Subnet ID', name: 'SubnetId')
        string(defaultValue: "ami-0fb7513bcdc525c3b", description: 'Enter Image ID', name: 'ImageId')
        string(defaultValue: "mohammad.ayub@industry.nsw.gov.au", description: 'Enter System Owners email address', name: 'SystemOwner')
        string(defaultValue: "amzon", description: 'Enter OS', name: 'OS')
        string(defaultValue: "mohammad-resize-dest", description: 'Enter Bucket Name', name: 'BucketName')
    }
    stages{
        stage('template copy'){
            steps {
                git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
                sh "cp single_instance_tariq.yaml /root/jenkins.aws.templates/single_instance_tariq.yaml"
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
                sh "/root/.local/lib/aws/bin/aws cloudformation create-stack --stack-name deploy-moh-cli-test-amapoc-moh-private-web-3\
                --template-body file:///root/jenkins.aws.templates/single_instance_tariq.yaml --parameters\
                ParameterKey=SubnetId,ParameterValue=${params.SubnetId} ParameterKey=ImageId,ParameterValue=${params.ImageId} \
                ParameterKey=SystemOwner,ParameterValue=${params.SystemOwner} ParameterKey=OS,ParameterValue=${params.OS}"
            }
        }
    }
}