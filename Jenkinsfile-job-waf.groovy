node ('slave') {
  
  parameters {
        string(defaultValue: "deploy-moh-cli-test-amapoc-moh-private-web-3", description: 'Enter Stack Name', name: 'StackName')
        string(defaultValue: "subnet-061b3d9a7f8b88210", description: 'Enter Subnet ID', name: 'SubnetId')
        string(defaultValue: "sg-0accba28318e4819c", description: 'Enter Security Group', name: 'SecurityGroupIds')
        string(defaultValue: "ami-0628ef1f10e34307d", description: 'Enter Image ID', name: 'ImageId')
        string(defaultValue: "mohammad.ayub@industry.nsw.gov.au", description: 'Enter System Owners email address', name: 'SystemOwner')
        string(defaultValue: "windows", description: 'Enter OS', name: 'OS')
        string(defaultValue: "amapocmoh-4", description: 'Enter Host Name', name: 'HostName')
        string(defaultValue: "arn:aws:sns:ap-southeast-2:820636345429:mohammad-ping-poc-topic", description: 'Enter SNS Topics', name: 'SNSTopicARN')
        string(defaultValue: "mohammad-resize-dest", description: 'Enter Bucket Name', name: 'AWSBucketName')
    }

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