#!/usr/bin/env bash
# create_tables_that_dont_exist.sh - Creates tables that do not exist
# Luis Rodero Merino - lrodero@stratio.com

##########################
## Variables definition ##
##########################
PROGNAME=$(basename $0)
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
USER=sanitas
PASSWD=Sanitas2016
HOST=scd-postgresbdc
PORT=5432
DB=sanitasdb
SCHEMA=seguros
MODIFY_ALL=0   # default value
TABLE_NAME=$1

##########################
#     Functions Area     #
##########################
# DO NOT MODIFY ANYTHING #
#    WITHIN THIS AREA    #
##########################
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

function test_psql {
#   ----------------------------------------------------------------
#   Function for testing whether psql command is installed
#   ----------------------------------------------------------------
    if test -e /usr/bin/psql; then
        echo "Psql... OK!"
    else
        error_exit "There is no Psql client installed"
    fi
}
function create {
#   ----------------------------------------------------------------
#   Function for deleting a table in a remote postgres
#   ----------------------------------------------------------------
    echo -n "Running create table script and add data Test... "
    #sanitas database tables creation
    (psql -v ON_ERROR_STOP=1 postgresql://postgres:stratio@scd-postgresbdc:5432/sanitasdb -f $DIR/tables_creation_and_data.sql > /dev/null && echo "OK!") || error_exit "Error creating structure"

    echo -n "End create table script... "
}

####################################
#         EXECUTION AREA           #
####################################
#          DANGER ZONE             #
# DONT MODIFY ANYTHING BEYOND THIS #
# LINE IF YOU ARE NOT SURE         #
####################################

test_psql
create

echo "Finished!!"
