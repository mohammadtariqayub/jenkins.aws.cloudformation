stage('s3-list-CD'){
    steps {
        sh 'echo ${env:BucketName}'
    }