stage('list s3 bucket'){
    steps {
        echo "Bucket Name: ${env:BucketName}"
        echo "Bucket Name: ${params.BucketName}"
    }