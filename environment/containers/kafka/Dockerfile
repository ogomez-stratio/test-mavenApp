FROM sanitas-base

# Configuration file (kafka is installed in /opt/sds/kafka )
ADD conf/server.properties /etc/sds/kafka/server.properties

# Bootstrap script
ADD bootstrap.sh /etc/bootstrap.sh
RUN chown root:root /etc/bootstrap.sh
RUN chmod 700 /etc/bootstrap.sh

WORKDIR /opt/

ENV BOOTSTRAP /etc/bootstrap.sh

ENTRYPOINT ["/etc/bootstrap.sh"]
