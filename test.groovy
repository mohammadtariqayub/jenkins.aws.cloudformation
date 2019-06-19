node ('slave') {
  
  parameters {
        string(defaultValue: "deploy-moh-cli-test-amapoc-moh-private-1", description: 'Enter Stack Name', name: 'StackName')
        string(defaultValue: "amapocmoh-1", description: 'Enter Host Name', name: 'HostName')
        string(defaultValue: "windows", description: 'Enter OS', name: 'OS')
        string(defaultValue: "ami-0628ef1f10e34307d", description: 'Enter Image ID', name: 'ImageID')
        string(defaultValue: "subnet-061b3d9a7f8b88210", description: 'Enter Subnet ID', name: 'SubnetId')
        string(defaultValue: "t3.small", description: 'Enter Instance Type', name: 'InstanceType')
        string(defaultValue: "test", description: 'Environment type. eg, prd', name: 'Environment')
        string(defaultValue: "true", description: 'Specifiy is the instance should join  Active Directory domain.', name: 'JoinDomain')
        string(defaultValue: "businesshours", description: 'A valid power schedule. eg, 247', name: 'GorillaStack')
        string(defaultValue: "aws-disrd-poc", description: 'Name of the AWS Account associated with this instance', name: 'AWSAccount')
        string(defaultValue: "BTSManagedInstanceProfile", description: 'BTSManagedInstanceProfile', name: 'InstanceProfile')
        string(defaultValue: "DOI-POC-MOHAMMAD-ACCESS-KEY", description: 'Enter EC2 Key', name: 'KeyPairName')
        string(defaultValue: "sg-0accba28318e4819c", description: 'Enter Security Group', name: 'SecurityGroupId')
        string(defaultValue: "https://amaprdsymprxelb01.dpi.nsw.gov.au:8080", description: 'Proxy address.', name: 'Proxy')
        string(defaultValue: "arn:aws:sns:ap-southeast-2:820636345429:mohammad-ping-poc-topic", description: 'Enter the SNS Topic ARN to subscribe to', name: 'SNSTopicARN')
        string(defaultValue: "mohammad.ayub@industry.nsw.gov.au", description: 'Enter System Owners email address', name: 'SystemOwner')        
        string(defaultValue: "mohammad-resize-dest", description: 'Enter Bucket Name', name: 'BucketName')
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

  stage('ec2-deploy') {
    echo "deploying ec2 inatance"
    build job: 'ec2-windows-poc-CD',
           parameters: [
                string(name: 'StackName', value: params['StackName']),
                string(name: 'HostName', value: params['HostName']),
                string(name: 'OS', value: params['OS']),
                string(name: 'ImageID', value: params['ImageID']),
                string(name: 'SubnetId', value: params['SubnetId']),
                string(name: 'InstanceType', value: params['InstanceType']),
                string(name: 'Environment', value: params['Environment']),
                string(name: 'JoinDomain', value: params['JoinDomain']),
                string(name: 'GorillaStack', value: params['GorillaStack']),
                string(name: 'AWSAccount', value: params['AWSAccount']),
                string(name: 'InstanceProfile', value: params['InstanceProfile']),
                string(name: 'KeyPairName', value: params['KeyPairName']),
                string(name: 'SecurityGroupId', value: params['SecurityGroupId']),
                string(name: 'Proxy', value: params['Proxy']),
                string(name: 'SNSTopicARN', value: params['SNSTopicARN']),
                string(name: 'SystemOwner', value: params['SystemOwner'])
           ]
  }

}