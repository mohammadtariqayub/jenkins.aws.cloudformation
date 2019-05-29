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
    }
}
   