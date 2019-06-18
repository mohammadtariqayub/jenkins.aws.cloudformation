   pipeline {
        agent { label 'slave' }

        parameters {
            string(defaultValue: "mohammad-resize-dest", description: 'Enter Bucket Name', name: 'BucketName')
        }

        stages {
            stage('template copy'){
            steps {
                git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
                sh "cp single_instance_generic.yaml /root/jenkins.aws.templates/single_instance_generic.yaml"
                }
            }
            stage('list s3 bucket'){
                    build 's3-list-CD'
            }
        }
    }