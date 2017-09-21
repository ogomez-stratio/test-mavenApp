#!/usr/bin/env bash
echo "**********************************************"
echo "SANITAS Environment Images: Building!"
echo "Profile: " $SCD_PROFILES_ACTIVE
echo "**********************************************"

## Default value SCD_PROFILES_ACTIVE = dev
if [ -z "$SCD_PROFILES_ACTIVE" ]
then
  export SCD_PROFILES_ACTIVE="dev"
fi


CURRENT_DIR=$PWD

if [[ "$SCD_PROFILES_ACTIVE" == "dev" ]]
then
    #base
    docker build -t sanitas-base containers/base/
    #zookeeper
    docker build -t sanitas-zookeeper containers/zookeeper/
    #kafka
    docker build -t sanitas-kafka containers/kafka/
    #deployment
    docker build -t sanitas-deployment containers/deployment/
    #provision
    docker build -t sanitas-provision containers/provision/
fi

# SCD ###################
#scdcommon-util
#cd ../scdcommon-util && mvn clean install && cd $CURRENT_DIR

#scddatahs
cd ../scddatanps     && mvn clean package docker:build && cd $CURRENT_DIR

echo "**********************************************"
echo "SANITAS Environment Images are build succesfully!"
echo "Profile: "  $SCD_PROFILES_ACTIVE
echo "**********************************************"
exit 0
