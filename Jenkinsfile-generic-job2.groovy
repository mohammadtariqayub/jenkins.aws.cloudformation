pipeline {
    agent { label 'slave' }

    parameters {
        string(defaultValue: "subnet-061b3d9a7f8b88210", description: 'Enter Subnet ID', name: 'SubnetId')
        string(defaultValue: "ami-0fb7513bcdc525c3b", description: 'Enter Image ID', name: 'ImageId')
        string(defaultValue: "mohammad-resize-dest", description: 'Enter Bucket Name', name: 'BucketName')
    }
    stages{
        stage('template copy'){
            steps {
                git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
                sh "cp Jenkinsfile-windows.groovy /root/jenkins.aws.templates/Jenkinsfile-windows.groovy"
            }
        }
        stage('list s3 bucket'){
            steps {
                echo "Bucket Name: ${params.BucketName}"
                sh "/root/.local/lib/aws/bin/aws s3 ls ${params.BucketName}"
            }
        }
        
    }
        stage ('vpc_creation') {
        echo "Deploying VPC toDeploying ec2 to POC VPC " 
        build job {'AWSEc2-windows-creation-CD',
          parameters: [
            string(name: 'AWSAccountNumber', value: params['AWSAccountNumber']),
            string(name: 'Environment', value: params['Environment']),
            string(name: 'BusinessOwner', value: params['BusinessOwner']),
            string(name: 'VPCCidr', value: params['VPCCidr']),
            string(name: 'VPCPurpose', value: params['VPCPurpose']),
            string(name: 'VPCName', value: params['VPCName']),
            string(name: 'CidrWebSubnetA', value: params['CidrWebSubnetA']),
            string(name: 'CidrWebSubnetB', value: params['CidrWebSubnetB']),
            string(name: 'CidrWebSubnetC', value: params['CidrWebSubnetC']),
            string(name: 'CidrPrivateSubnetA', value: params['CidrPrivateSubnetA']),
            string(name: 'CidrPrivateSubnetB', value: params['CidrPrivateSubnetB']),
            string(name: 'CidrPrivateSubnetC', value: params['CidrPrivateSubnetC']),
            string(name: 'DHCPOptionsDomainName', value: params['DHCPOptionsDomainName']),
            string(name: 'DHCPOptionsDomainNameServers', value: params['DHCPOptionsDomainNameServers']),
            booleanParam(name: 'CreateIGWFlag', value: params['CreateIGWFlag'])
          ]
        }
        }
    }
}
