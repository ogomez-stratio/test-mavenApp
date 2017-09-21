#!/usr/bin/env bash
# initialize_ingestion.sh - Copy ingestion config folder
# Mario Fernández Martínez - mariofernandez@stratio.com

set -e

##########################
## Variables definition ##
##########################
PROGNAME=$(basename $0)
FORCE=0
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
source $DIR/kafka_commons.sh

#########################
#     Functions Area     #
##########################
# DO NOT MODIFY ANYTHING #
#    WITHIN THIS AREA    #
##########################
function usage {
    echo "Usage: initialize_ingestion.sh [-f]"
}

function check_params {
#   ----------------------------------------------------------------
#   Function for checking whether the program is executed properly
#       Accepts 1 argument:
#           array containing program execution parameters
#   ----------------------------------------------------------------
    while getopts ":f" o; do
        case "${o}" in
            f)
                FORCE=1
                echo " > Forcing..."
                ;;
            *)
                usage
                ;;
        esac
    done

    COUNT_PARAMS=0

    if [ $FORCE -eq 1 ]; then
         COUNT_PARAMS=1
    fi

    if [ "$#" -ne "$COUNT_PARAMS" ]; then
        usage
        exit 1
    fi
}

function run {
    for t in "${TOPICS[@]}"
    do
        $DIR/create_topic.sh -f ${t}
    done
}

####################################
#         EXECUTION AREA           #
####################################
#          DANGER ZONE             #
# DONT MODIFY ANYTHING BEYOND THIS #
# LINE IF YOU ARE NOT SURE         #
####################################
test_kafka
check_params $@

if [ $FORCE -eq 0 ]; then
    echo "This will initialize kafka topics in ZK=$ZK. Are you sure?"
    select yn in "Yes" "No"; do
        case $yn in
            Yes )
                break
            ;;
            No )
                echo "Aborted"
                exit
            ;;
        esac
    done
fi
run
echo "Finished"
