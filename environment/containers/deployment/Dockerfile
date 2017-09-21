FROM sanitas-base

# install postgres cli
RUN apt-get update && apt-get -y install postgresql-client

# JQ 1.5
RUN apt-get update && apt-get -y install build-essential
RUN wget https://github.com/stedolan/jq/releases/download/jq-1.5/jq-1.5.tar.gz -O /tmp/jq-1.5.tar.gz
RUN tar -xzf /tmp/jq-1.5.tar.gz -C /opt
RUN mv /opt/jq-1.5 /opt/jq
RUN cd /opt/jq && ./configure && make && make install && cd -
RUN ln -s /opt/jq/jq /usr/bin/jq

# Adding deployment scripts
ADD scripts /opt/sds/deployment/scripts

WORKDIR /opt/sds/deployment/scripts
RUN chmod -R 750 /opt/sds/deployment/scripts

# necessary for ssh server
RUN mkdir /var/run/sshd


CMD ["/usr/sbin/sshd", "-D"]

