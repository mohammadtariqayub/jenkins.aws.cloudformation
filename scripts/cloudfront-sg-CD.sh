#!/usr/bin/bash
# deploying cerificate in ap_southeast_2
StackName="$StackName-sg-$Application"
echo "deploying Stack $StackName"

/root/.local/lib/aws/bin/aws cloudformation create-stack --stack-name $StackName --capabilities CAPABILITY_NAMED_IAM \
--template-body file:///root/jenkins.aws.cloudformation/cloudformation/cloudfront-elb-security-groups.yml --parameters \
ParameterKey=Application,ParameterValue=$Application \
ParameterKey=AWSAccountSSMParameter,ParameterValue=$AWSAccountSSMParameter \
ParameterKey=Comments,ParameterValue=$Comments \
ParameterKey=DeploymentBucket,ParameterValue=$DeploymentBucket \
ParameterKey=ProjectCode,ParameterValue=$ProjectCode \
ParameterKey=Environment,ParameterValue=$Environment \
ParameterKey=VpcId,ParameterValue=$VpcId \
ParameterKey=SystemOwner,ParameterValue=$SystemOwner

# Wait for stack to complete
FinalStatus=`/root/.local/lib/aws/bin/aws cloudformation describe-stacks --stack-name $StackName |grep StackStatus |cut -d ":" -f2 |sed 's/[", ]//g'`
while [ "$FinalStatus" != "CREATE_COMPLETE" ]
do
    sleep 60
    FinalStatus=`/root/.local/lib/aws/bin/aws cloudformation describe-stacks --stack-name $StackName |grep StackStatus |cut -d ":" -f2 |sed 's/[", ]//g'`
    echo $FinalStatus
done
# Get SecurityGroup ID
sg_ID=`/root/.local/lib/aws/bin/aws cloudformation describe-stack-resources --stack-name $StackName |grep PhysicalResourceId |grep sg |cut -d '"' -f4`
filename_sg="sg_$StackName"
echo Security Group created are $sg_ID
echo $sg_ID > /root/artifacts/$filename_sg.txt

# Get lambda function ID
lambda_ID=`/root/.local/lib/aws/bin/aws cloudformation describe-stack-resources --stack-name $StackName |grep PhysicalResourceId |grep function |cut -d '"' -f4`
filename_lambda="lambda_$StackName"
echo Security Group created are $lambda_ID
echo $sg_ID > /root/artifacts/$filename_lambda.txt