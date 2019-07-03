#!/usr/bin/bash
StackName="$StackName-acm-$Application"
echo $StackName

/root/.local/lib/aws/bin/aws cloudformation create-stack --stack-name $StackName \
--template-body file:///root/jenkins.aws.cloudformation/cloudformation/acm-provision-ssl-certificate.yml --parameters \
ParameterKey=ApexDomainName,ParameterValue=$ApexDomainName \
ParameterKey=Application,ParameterValue=$Application \
ParameterKey=BusinessOwner,ParameterValue=$BusinessOwner \
ParameterKey=BusinessUnit,ParameterValue=$BusinessUnit \
ParameterKey=Comments,ParameterValue=$Comments \
ParameterKey=DomainName,ParameterValue=$DomainName \
ParameterKey=Environment,ParameterValue=$Environment \
ParameterKey=ProjectCode,ParameterValue=$ProjectCode \
ParameterKey=RecID,ParameterValue=$RecID \
ParameterKey=RFC,ParameterValue=$RFC \
ParameterKey=SystemOwner,ParameterValue=$SystemOwner \
ParameterKey=AWSAccountSSMParameter,ParameterValue=$AWSAccountSSMParameter 

# Wait for stack to complete
FinalStatus=`/root/.local/lib/aws/bin/aws cloudformation describe-stacks --stack-name $StackName |grep StackStatus |cut -d ":" -f2 |sed 's/[", ]//g'`
while [ "$FinalStatus" != "CREATE_COMPLETE" ]
do
    sleep 30
    FinalStatus=`/root/.local/lib/aws/bin/aws cloudformation describe-stacks --stack-name $StackName |grep StackStatus |cut -d ":" -f2 |sed 's/[", ]//g'`
    echo $FinalStatus
done
# Get ACM ID
acm_arn_ap_southeast_2 = `/root/.local/lib/aws/bin/aws cloudformation describe-stack-resources --stack-name $StackName |grep PhysicalResourceId |cut -d '"' -f4`
filename = acm_arn_ap_southeast_2_$StackName
echo ACM ARN ap-southeast-2 is $acm_arn_ap_southeast_2
echo $acm_arn_ap_southeast_2 > /root/artifacts/$filename.txt
