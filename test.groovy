node ('slave') {
  stage ('parameter_check') {
    def validBucketName = (params['BucketName'] ==~ /^(?!\s*$).+/)
    if (!validBucketName) { error "Invalid parameter BusinessOwner. Parameter cannot be empty!" }
    
    echo "parameter_check stage complete!!"
  }

  stage('template copy'){
            git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
            sh "cp single_instance_generic.yaml /root/jenkins.aws.templates/single_instance_generic.yaml"
  }

  stage('list s3 bucket') {
    echo "Listing s3 bucket"
    build job: 's3-list-CD',
           parameters: [
                string(name: 'BucketName', value: params['BucketName'])
           ]
  }
}