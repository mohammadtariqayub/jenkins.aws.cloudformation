parameters {
        string(defaultValue: "", description: 'K', name: 'HELLO')
    }

stage('list s3 bucket'){
    steps {
        sh 'echo ${env:BucketName}'
        sh 'echo "Bucket Name: ${HELLO}"'
    }