#!/usr/bin/env bash

if [ -z "$ZK" ]; then ZK="scd-zookeeper:2181"; fi
if [ -z "$PARTITIONS" ]; then PARTITIONS=1; fi
if [ -z "$REPLICATION_FACTOR" ]; then REPLICATION_FACTOR=1; fi

COMMAND=/opt/sds/kafka/bin/kafka-topics.sh

TOPICS=()


test_kafka() {
#   ----------------------------------------------------------------
#   Function for testing whether kafka command is installed
#   ----------------------------------------------------------------
    if test -e $COMMAND; then
        echo "Kafka... OK!"
    else
        error_exit "There is no Kafka client installed"
    fi
}
