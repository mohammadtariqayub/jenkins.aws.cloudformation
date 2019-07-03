node ('slave') {
  
  stage ('parameter_check') {
    def validAWSBucketName = (params['AWSBucketName'] ==~ /^(?!\s*$).+/)
    if (!validAWSBucketName) { error "Invalid parameter Bucket Name. Parameter cannot be empty!" }
    
    echo "parameter_check stage complete!!!"
  }

  stage('template copy'){
    git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
    sh "cp ./scripts/s3-list-CD.sh /root/jenkins.aws.cloudformation/scripts/s3-list-CD.sh"
    sh "cp ./scripts/ec2-windows-poc-CD.sh /root/jenkins.aws.cloudformation/scripts/ec2-windows-poc-CD.sh"
    }

  stage('list s3 bucket') {
    echo "Listing s3 bucket"
    build job: 's3-list-CD',
           parameters: [
                string(name: 'AWSBucketName', value: params['AWSBucketName'])
           ]
  }
}