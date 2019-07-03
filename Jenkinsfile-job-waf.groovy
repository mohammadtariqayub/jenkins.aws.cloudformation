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
    sh "cp ./scripts/acm-cert-CD.sh /root/jenkins.aws.cloudformation/scripts/acm-cert-CD.sh"
    sh "cp ./cloudformation/acm-provision-ssl-certificate.yml /root/jenkins.aws.cloudformation/cloudformation/acm-provision-ssl-certificate.yml"
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
                string(name: 'SystemOwner', value: params['SystemOwner']),
                string(name: 'AWSAccountSSMParameter', value: params['AWSAccountSSMParameter'])
           ]
  }
}