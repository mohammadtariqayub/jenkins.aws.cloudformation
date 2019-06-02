stage('list s3 bucket'){
    steps {
        sh echo "Bucket Name: ${env:BucketName}"
        sh echo "Bucket Name: ${params.BucketName}"
    }