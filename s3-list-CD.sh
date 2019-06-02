parameters {
        string(defaultValue: "", description: 'K', name: 'BucketName')
    }

stage('list s3 bucket'){
    steps {
        sh 'echo ${env:BucketName}'
        sh 'echo "Bucket Name: ${params.BucketName}"'
    }