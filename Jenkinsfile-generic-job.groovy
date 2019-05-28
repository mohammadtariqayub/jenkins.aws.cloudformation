pipeline {
    agent { label 'slave' }

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
}

   