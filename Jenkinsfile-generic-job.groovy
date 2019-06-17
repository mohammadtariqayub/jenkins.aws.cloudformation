agent { label 'slave' }
  stage('template copy'){
    steps {
           git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
           sh "cp single_instance_generic.yaml /root/jenkins.aws.templates/single_instance_generic.yaml"
        }
    }
  stage ('list s3 bucket'){
  echo "Deploying s3 list"
  build job: 's3-list-CD',
             parameters: [
             string(name: 'BucketName', value: params['BucketName'])
             ]  
  }  
            