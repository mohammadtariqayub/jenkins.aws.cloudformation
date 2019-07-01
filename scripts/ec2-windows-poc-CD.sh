FinalStatus=`/root/.local/lib/aws/bin/aws cloudformation describe-stacks --stack-name deploy-moh-cli-test-amapoc-moh-private-1 |grep StackStatus |cut -d ":" -f2 |sed 's/[", ]//g'`
echo $FinalStatus