#!/usr/bin/env bash
# create_topic.sh - Creates one topics in kafka (if not existing)
# Mario Fernández Martínez - mariofernandez@stratio.com

##########################
## Variables definition ##
##########################
PROGNAME=$(basename $0)
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
FORCE=0
source $DIR/kafka_commons.sh

##########################
#     Functions Area     #
##########################
# DO NOT MODIFY ANYTHING #
#    WITHIN THIS AREA    #
##########################
function usage {
    echo "Usage: create_topic [-f] \"<new topic name>"
}


function error_exit
{
#   ----------------------------------------------------------------
#   Function for exit due to fatal program error
#       Accepts 1 argument:
#           string containing descriptive error message
#   ----------------------------------------------------------------

    echo "${PROGNAME} - ${1:-"Unknown Error"}" 1>&2
    exit 1
}

function check_params {
#   ----------------------------------------------------------------
#   Function for checking whether the program is executed properly
#       Accepts 1 argument:
#           array containing program execution parameters
#   ----------------------------------------------------------------
    PARAM=$1
    while getopts ":f" o; do
        case "${o}" in
            f)
                FORCE=1
                PARAM=$2
                echo " > Forcing..."
                ;;
            *)
                usage
                ;;
        esac
    done
}



function create {
#   ----------------------------------------------------------------
#   Function for creating a topic
#       Accepts 1 argument:
#           string Topic name
#   ----------------------------------------------------------------
    topic_name=$($COMMAND --list --zookeeper $ZK | grep $1)
    if [ -z "$topic_name" ]
    then
        echo -n "Creating topic $1... "
        ($COMMAND --create --zookeeper $ZK --replication-factor $REPLICATION_FACTOR --partitions $PARTITIONS --topic $1 > /dev/null && echo "OK!") || error_exit "Error creating topic $1"
    else
        echo "Topic $1 already exists, skipping!"
    fi
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
    echo "Topic $1 will be created. Are you sure?"
    select YN in "Yes" "No"; do
        case $YN in
            Yes)
                create $PARAM
                break
            ;;
            No)
                echo "Aborted"
                exit
            ;;
        esac
    done
else
    create $PARAM
fi
echo "Finished!!"
