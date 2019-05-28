pipeline {
    agent { label 'slave' }

    stage('template copy'){
            steps {
                git url: 'https://github.com/mohammadtariqayub/jenkins.aws.cloudformation.git'
                sh "cp single_instance_generic.yaml /root/jenkins.aws.templates/single_instance_generic.yaml"
            }
        }
}

   