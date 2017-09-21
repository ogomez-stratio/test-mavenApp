FROM sanitas-base

# Install utilities
RUN apt-get update && apt-get -y install postgresql-client

# postgrebd user creation
ADD postgres/init-user-db.sh /opt/provision/scripts/init-user-db.sh
RUN chmod 700 /opt/provision/scripts/init-user-db.sh
ADD postgres/user_creation.sql /opt/provision/scripts/user_creation.sql


# kafka topic creation
ADD kafka/topic-creation.sh /opt/provision/scripts/topic-creation.sh
RUN chmod 700 /opt/provision/scripts/topic-creation.sh


# Bootstrap script
ADD bootstrap.sh /opt/provision/scripts/bootstrap.sh
RUN chmod 700 /opt/provision/scripts/bootstrap.sh

WORKDIR /opt/provision/scripts

ENTRYPOINT ["/opt/provision/scripts/bootstrap.sh"]