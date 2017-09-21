#!/usr/bin/env bash
echo "**********************************************"
echo "SANITAS Environment Images: Building!"
echo "Profile: " $SCD_PROFILES_ACTIVE
echo "**********************************************"

## Default value SCD_PROFILES_ACTIVE = develop
if [ -z "$SCD_PROFILES_ACTIVE" ]
then
  export SCD_PROFILES_ACTIVE="dev"
fi

if [ "$SCD_PROFILES_ACTIVE" == "dev" ]
then
    CURRENT_DIR=$PWD

    #scddatahs
    cd ../scddatanps && mvn clean package -P war && cd $CURRENT_DIR
fi

echo "**********************************************"
echo "SANITAS Environment Images are build succesfully!"
echo "Profile: "  $SCD_PROFILES_ACTIVE
echo "**********************************************"
exit 0