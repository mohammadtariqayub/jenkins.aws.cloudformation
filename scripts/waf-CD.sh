#!/usr/bin/bash
# deploying WAF
StackName_waf="$StackName-$Application-waf"
echo "deploying Stack $StackName_waf"
echo $StackName
echo $Application
echo $BusinessOwner

#Defining parameters
AppAccessLogBucket="$BusinessOwner-$Application-waf-logs"
echo $AppAccessLogBucket
ActivateSqlInjectionProtectionParam="yes"
echo $ActivateSqlInjectionProtectionParam
ActivateCrossSiteScriptingProtectionParam="yes"
echo $ActivateCrossSiteScriptingProtectionParam
ActivateHttpFloodProtectionParam="yes - AWS WAF rate based rule"
echo $ActivateHttpFloodProtectionParam
ActivateScannersProbesProtectionParam="yes - AWS Lambda log parser"
echo $ActivateScannersProbesProtectionParam
ActivateBadBotProtectionParam="yes"
echo $ActivateBadBotProtectionParam
EndpointType="CloudFront"
echo $EndpointType
RequestThreshold="2000"
echo $RequestThreshold
ErrorThreshold="50"
echo $ErrorThreshold
WAFBlockPeriod="240"
echo $WAFBlockPeriod
ApiGatewayBadBotName="$Application-$BusinessOwner"
echo $ApiGatewayBadBotName

/root/.local/lib/aws/bin/aws cloudformation create-stack --stack-name $StackName_waf \
--template-body file:///root/jenkins.aws.cloudformation/cloudformation/deploy-waf-v2.3.yml --parameters \
ParameterKey=AppAccessLogBucket,ParameterValue=$AppAccessLogBucket \
ParameterKey=ActivateSqlInjectionProtectionParam,ParameterValue=$ActivateSqlInjectionProtectionParam \
ParameterKey=ActivateCrossSiteScriptingProtectionParam,ParameterValue=$ActivateCrossSiteScriptingProtectionParam \
ParameterKey=ActivateHttpFloodProtectionParam,ParameterValue=$ActivateHttpFloodProtectionParam \
ParameterKey=ActivateScannersProbesProtectionParam,ParameterValue=$ActivateScannersProbesProtectionParam \
ParameterKey=ActivateReputationListsProtectionParam,ParameterValue=$ActivateReputationListsProtectionParam \
ParameterKey=ActivateBadBotProtectionParam,ParameterValue=$ActivateBadBotProtectionParam \
ParameterKey=EndpointType,ParameterValue=$EndpointType \
ParameterKey=RequestThreshold,ParameterValue=$RequestThreshold \
ParameterKey=ErrorThreshold,ParameterValue=$ErrorThreshold \
ParameterKey=WAFBlockPeriod,ParameterValue=$WAFBlockPeriod \
ParameterKey=ApiGatewayBadBotName,ParameterValue=$ApiGatewayBadBotName

# Wait for stack to complete
FinalStatus=`/root/.local/lib/aws/bin/aws cloudformation describe-stacks --stack-name $StackName_waf |grep StackStatus |cut -d ":" -f2 |sed 's/[", ]//g'`
while [ "$FinalStatus" != "CREATE_COMPLETE" ]
do
    sleep 120
    FinalStatus=`/root/.local/lib/aws/bin/aws cloudformation describe-stacks --stack-name $StackName_waf |grep StackStatus |cut -d ":" -f2 |sed 's/[", ]//g'`
    echo $FinalStatus
done
