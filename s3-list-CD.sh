stage('list s3 bucket'){
    steps {
        sh 'echo ${env:BucketName}'
    }