sh "/root/.local/lib/aws/bin/aws cloudformation create-stack --stack-name ${params.StackName} \
--template-body file:///root/jenkins.aws.templates/single_instance_windows-cutomized.yaml --parameters \
ParameterKey=SubnetId,ParameterValue=${params.SubnetId} ParameterKey=ImageID,ParameterValue=${params.ImageID} \
ParameterKey=SystemOwner,ParameterValue=${params.SystemOwner} ParameterKey=OS,ParameterValue=${params.OS} \
ParameterKey=HostName,ParameterValue=${params.HostName} ParameterKey=SecurityGroupId,ParameterValue=${params.SecurityGroupId} \
ParameterKey=InstanceType,ParameterValue=${params.InstanceType} ParameterKey=InstanceProfile,ParameterValue=${params.InstanceProfile} \
ParameterKey=KeyPairName,ParameterValue=${params.KeyPairName} ParameterKey=Environment,ParameterValue=${params.Environment} \
ParameterKey=AWSAccount,ParameterValue=${params.AWSAccount} ParameterKey=GorillaStack,ParameterValue=${params.GorillaStack} \
ParameterKey=SNSTopicARN,ParameterValue=${params.SNSTopicARN} ParameterKey=JoinDomain,ParameterValue=${params.JoinDomain} \
ParameterKey=Proxy,ParameterValue=${params.Proxy}" 
FinalStatus=`/root/.local/lib/aws/bin/aws cloudformation describe-stacks --stack-name deploy-moh-cli-test-amapoc-moh-private-1 |grep StackStatus |cut -d ":" -f2 |sed 's/[", ]//g'`
echo $FinalStatus