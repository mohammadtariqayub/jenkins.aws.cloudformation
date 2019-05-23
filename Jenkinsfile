pipeline {
    agent { label 'slave' }

    stages{
        stage ('checkout-repos') {
            echo "Checking out jenkins.aws.cloudformation repo..."
            git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git', branch:'master'
            echo "Checked out jenkins.aws.cloudformation repo..."        
            echo "Copying stage files into /root/jenkins.aws.templates folder..."
            sh "cp * /root/jenkins.aws.templates"
            echo "script copied to /root/jenkins.aws.templates folder!"
    }
        
        
        stage('ec2-deploy'){
            steps {
                sh 'cd /root/jenkins.aws.templates/jenkins.aws.cloudformation'
                sh 'git pull origin master'
                sh '/root/.local/lib/aws/bin/aws cloudformation deploy --template-file /root/jenkins.aws.templates/single_instance_tariq.yaml --stack-name deploy-moh-cli-test-amapoc-moh-private-web-3'
            }
        }
   }
}
