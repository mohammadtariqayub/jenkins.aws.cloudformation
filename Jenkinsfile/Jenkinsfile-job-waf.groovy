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

  stage('create acm certificate') {
    echo "creating acm certificate"
    build job: 'acm-cert-CD',
           parameters: [
                string(name: 'StackName', value: params['StackName']),
                string(name: 'ApexDomainName', value: params['ApexDomainName']),
                string(name: 'Application', value: params['Application']),
                string(name: 'BusinessOwner', value: params['BusinessOwner']),
                string(name: 'BusinessUnit', value: params['BusinessUnit']),
                string(name: 'Comments', value: params['Comments']),
                string(name: 'DomainName', value: params['DomainName']),
                string(name: 'Environment', value: params['Environment']),
                string(name: 'ProjectCode', value: params['ProjectCode']),
                string(name: 'RecID', value: params['RecID']),
                string(name: 'RFC', value: params['RFC']),
                string(name: 'AWSAccount', value: params['AWSAccount']),
                string(name: 'SystemOwner', value: params['SystemOwner'])
           ]
  }

  stage('deploy security group') {
    echo "deploying security group"
    build job: 'cloudfront-sg-CD',
           parameters: [
                string(name: 'StackName', value: params['StackName']),
                string(name: 'Application', value: params['Application']),
                string(name: 'AWSAccountSSMParameter', value: params['AWSAccountSSMParameter']),
                string(name: 'Comments', value: params['Comments']),
                string(name: 'DeploymentBucket', value: params['DeploymentBucket']),
                string(name: 'ProjectCode', value: params['ProjectCode']),
                string(name: 'Environment', value: params['Environment']),
                string(name: 'VpcId', value: params['VpcId']),
                string(name: 'SystemOwner', value: params['SystemOwner'])
           ]
  }
  
    stage('deploy alb') {
    echo "deploying alb"
    build job: 'alb-CD',
           parameters: [
                string(name: 'StackName', value: params['StackName']),
                string(name: 'ALBScheme', value: params['ALBScheme']),
                string(name: 'ALBAcessLogBucket', value: params['ALBAcessLogBucket']),
                string(name: 'VpcId', value: params['VpcId']),
                string(name: 'PublicSubnetA', value: params['PublicSubnetA']),
                string(name: 'PublicSubnetB', value: params['PublicSubnetB']),
                string(name: 'PublicSubnetC', value: params['PublicSubnetC']),
                string(name: 'EC2Instance1', value: params['EC2Instance1']),
                string(name: 'EC2Instance2', value: params['EC2Instance2']),
                string(name: 'Application', value: params['Application']),
                string(name: 'BusinessOwner', value: params['BusinessOwner']),
                string(name: 'BusinessUnit', value: params['BusinessUnit']),
                string(name: 'Criticality', value: params['Criticality']),
                string(name: 'Environment', value: params['Environment']),
                string(name: 'RecID', value: params['RecID']),
                string(name: 'SystemOwner', value: params['SystemOwner'])
           ]
  }
}