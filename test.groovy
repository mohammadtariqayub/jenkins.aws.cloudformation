node ('slave') {
  stage ('parameter_check') {
    def validBucketName = (params['BucketName'] ==~ /^(?!\s*$).+/)
    if (!validBusinessOwner) { error "Invalid parameter BusinessOwner. Parameter cannot be empty!" }
    
    echo "parameter_check stage complete!!"
  }
  
  stage('list s3 bucket') {
    echo "Listing s3 bucket"
    build job: 's3-list-CD',
           parameters: [
                string(name: 'BucketName', value: params['BucketName'])
            ]
  }