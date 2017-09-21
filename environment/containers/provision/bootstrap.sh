#!/bin/bash
echo ""
echo ""
echo "Starting provision at $(date +"%Y-%m-%d %H:%M:%S")"
#postgrebd
/opt/sanitas/scripts/wait-for-it.sh scd-postgresbdc:5432 -s -t 60 -- ./init-user-db.sh
#kafka
/opt/sanitas/scripts/wait-for-it.sh scd-kafka:9092 -s -t 60 -- ./topic-creation.sh

# finish!
echo "Enviroment started and provisioned, check above if there is some error"
echo ""