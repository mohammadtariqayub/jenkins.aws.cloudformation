        stage('list s3 bucket'){
            steps {
                echo "Bucket Name: ${params.BucketName}"
                sh "/root/.local/lib/aws/bin/aws s3 ls @{ParameterKey="BucketName"; ParameterValue=$env:BucketName},"
            }