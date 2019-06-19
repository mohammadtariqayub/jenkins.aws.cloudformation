node ('slave') {
  stage ('parameter_check') {
    def validAWSBucketName = (params['AWSBucketName'] ==~ /^(?!\s*$).+/)
    if (!validAWSBucketName) { error "Invalid parameter Bucket Name. Parameter cannot be empty!" }
    
    echo "parameter_check stage complete!!!"
  }

  stage('list s3 bucket') {
    echo "Listing s3 bucket"
    echo "Listing s3 bucket"
    build job: 's3-list-CD',
           parameters: [
                string(name: 'AWSBucketName', value: params['AWSBucketName']),
           ]
  }
}