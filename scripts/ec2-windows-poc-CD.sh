echo hello
/root/.local/lib/aws/bin/aws cloudformation create-stack --stack-name $StackName \
--template-body file:///root/jenkins.aws.cloudformation/single_instance_windows-cutomized.yaml --parameters \
ParameterKey=SubnetId,ParameterValue=$SubnetId ParameterKey=ImageID,ParameterValue=$ImageID \
ParameterKey=SystemOwner,ParameterValue=$SystemOwner ParameterKey=OS,ParameterValue=$OS \
ParameterKey=HostName,ParameterValue=$HostName ParameterKey=SecurityGroupId,ParameterValue=$SecurityGroupId \
ParameterKey=InstanceType,ParameterValue=$InstanceType ParameterKey=InstanceProfile,ParameterValue=$InstanceProfile \
ParameterKey=KeyPairName,ParameterValue=$KeyPairName ParameterKey=Environment,ParameterValue=$Environment \
ParameterKey=AWSAccount,ParameterValue=$AWSAccount ParameterKey=GorillaStack,ParameterValue=$GorillaStack \
ParameterKey=SNSTopicARN,ParameterValue=$SNSTopicARN ParameterKey=JoinDomain,ParameterValue=$JoinDomain \
ParameterKey=Proxy,ParameterValue=$Proxy