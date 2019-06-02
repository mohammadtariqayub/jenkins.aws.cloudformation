pipeline {
    agent { label 'slave' }

    stages{
        stage ('parameter_check') {
          def validBucketName = (params['BucketName'] ==~ /^(?!\s*$).+/)
          if (!validBucketName) { error "Invalid parameter BucketName. Should [0-9]{12}!" }
        }

        stage('template copy'){
            steps {
                git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
                sh "cp single_instance_generic.yaml /root/jenkins.aws.templates/single_instance_generic.yaml"
            }
        }
        stage ('list s3 bucket'){
          steps {
          build job: 's3-list-CD',
             parameters: [
             string(name: 'BucketName', value: params['BucketName'])
             ]  
           }  
        }
    }
        
}