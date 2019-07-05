node ('slave') {
  
  stage ('parameter_check') {
    def validAWSBucketName = (params['AWSBucketName'] ==~ /^(?!\s*$).+/)
    if (!validAWSBucketName) { error "Invalid parameter Bucket Name. Parameter cannot be empty!" }
    
    echo "parameter_check stage complete!!!"
  }

  stage('template copy'){
    git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
    sh "cp -R * /root/jenkins.aws.cloudformation/"
  }

  stage('deploy s3 buckets') {
    echo "deploy s3 buckets for WAF and CF"
    build job: 's3-bucket-CD',
           parameters: [
                string(name: 'StackName', value: params['StackName']),
                string(name: 'Application', value: params['Application']),
                string(name: 'EnvironmentType', value: params['EnvironmentType']),
                string(name: 'BusinessUnit', value: params['BusinessUnit']),
                string(name: 'Environment', value: params['Environment']),
                string(name: 'ProjectCode', value: params['ProjectCode']),
                string(name: 'RFC', value: params['RFC']),
                string(name: 'SystemOwner', value: params['SystemOwner'])
           ]
  }
}