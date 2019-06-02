pipeline {
    agent { label 'slave' }

        stage('parameter_check'){
          def validBucketName = (params['BucketName'] ==~ /^(?!\s*$).+/)
          if (!validBucketName) { error "Invalid parameter BucketName. Should [0-9]{12}!" }
        }
        stage('template copy'){
          steps {
           git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
           sh "cp Jenkinsfile-windows.groovy /root/jenkins.aws.templates/Jenkinsfile-windows.groovy"
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