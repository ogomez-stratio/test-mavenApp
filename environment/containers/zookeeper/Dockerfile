FROM sanitas-base

WORKDIR /opt

RUN echo `pwd`

RUN wget http://apache.uvigo.es/zookeeper/zookeeper-3.5.1-alpha/zookeeper-3.5.1-alpha.tar.gz
RUN tar -xzf zookeeper-3.5.1-alpha.tar.gz

RUN ln -s /opt/zookeeper-3.5.1-alpha /opt/sds/zookeeper

ADD conf /opt/sds/zookeeper/conf

CMD ["/opt/sds/zookeeper/bin/zkServer.sh", "start-foreground"]