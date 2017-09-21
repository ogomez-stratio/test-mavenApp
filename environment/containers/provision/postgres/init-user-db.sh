#!/bin/bash
set -e

####################################
#          DANGER ZONE             #
# DONT MODIFY ANYTHING BEYOND THIS #
# LINE IF YOU ARE NOT SURE         #
####################################

#wait 15 more second after port available, DB initialization
sleep 10s
echo "Creating Postgres users and databases..."

# user creation (if not exists)
psql -v ON_ERROR_STOP=1 postgresql://postgres:stratio@scd-postgresbdc:5432 -f user_creation.sql

###############################  SCD_HEALTHSCORE ############################################################
# sanitas database creation (if not exist) cannot be a file or a function, postgresql don't like database creation in function
psql -v ON_ERROR_STOP=1 postgresql://postgres:stratio@scd-postgresbdc:5432 -c "SELECT 1 FROM pg_database WHERE datname = 'sanitasdb'" |
grep -q 1 ||
psql -v ON_ERROR_STOP=1 postgresql://postgres:stratio@scd-postgresbdc:5432 -c "CREATE DATABASE sanitasdb"
#grants
psql -v ON_ERROR_STOP=1 postgresql://postgres:stratio@scd-postgresbdc:5432 -c "GRANT ALL PRIVILEGES ON DATABASE sanitasdb TO sanitas"

ssh scd-deployment "/opt/sds/deployment/scripts/postgres/scd_nps/create_tables_that_dont_exist_and_add_data.sh"
