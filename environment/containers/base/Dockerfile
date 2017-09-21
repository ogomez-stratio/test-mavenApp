#install from ubuntu
FROM ubuntu:14.04


# wait for it script
ADD common/wait-for-it.sh /opt/sanitas/scripts/wait-for-it.sh
RUN chmod 700 /opt/sanitas/scripts/wait-for-it.sh

# Setting debconf to non interactive
RUN echo 'debconf debconf/frontend select Noninteractive' | debconf-set-selections
# RUN echo "deb http://security.ubuntu.com/ubuntu trusty-security main" >>  /etc/apt/sources.list

# Install some software
RUN sudo apt-get -y update
RUN sudo apt-get -yq install software-properties-common
# Install java 8
RUN sudo add-apt-repository -y ppa:webupd8team/java
RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | sudo debconf-set-selections
RUN sudo apt-get -y update
RUN sudo apt-get install -yq --force-yes oracle-java8-installer

# Download Kafka and install it in /opt/sds
RUN wget http://apache.rediris.es/kafka/0.10.0.1/kafka_2.11-0.10.0.1.tgz -O /opt/kafka_2.11-0.10.0.1.tgz
RUN cd /opt/ && tar xzvf kafka_2.11-0.10.0.1.tgz
RUN mkdir /opt/sds
RUN ln -s /opt/kafka_2.11-0.10.0.1 /opt/sds/kafka

# Download Hadoop and install it in /opt/sds/hadoop
#RUN wget http://apache.rediris.es/hadoop/common/hadoop-2.7.2/hadoop-2.7.2.tar.gz -O /opt/hadoop-2.7.2.tar.gz
#RUN cd /opt/ && tar xf hadoop-2.7.2.tar.gz
#RUN ln -s /opt/hadoop-2.7.2 /opt/sds/hadoop
#ENV HADOOP_HOME /opt/sds/hadoop
## HDFS config in all nodes with HDFS
#RUN mv /opt/sds/hadoop/etc/hadoop/core-site.xml /opt/sds/hadoop/etc/hadoop/core-site.xml.bak
#RUN echo "<configuration><property><name>fs.defaultFS</name><value>hdfs://hadoop:8020</value></property></configuration>" > /opt/sds/hadoop/etc/hadoop/core-site.xml
#RUN mv /opt/sds/hadoop/etc/hadoop/hdfs-site.xml /opt/sds/hadoop/etc/hadoop/hdfs-site.xml.bak
#RUN echo "<configuration><property><name>dfs.replication</name><value>1</value></property><property><name>dfs.permissions</name><value>false</value></property></configuration>" > /opt/sds/hadoop/etc/hadoop/hdfs-site.xml
#ENV HADOOP_CONF_DIR /opt/sds/hadoop/etc/hadoop
#
## SET JAVA_HOME (needed to run hadoop)
#ENV JAVA_HOME=/usr/lib/jvm/java-8-oracle/
#RUN sed -i '/export JAVA_HOME=\${JAVA_HOME}/c export JAVA_HOME=/usr/lib/jvm/java-8-oracle/' /opt/sds/hadoop/etc/hadoop/hadoop-env.sh

# Install and configure SSH server (needed by hadoop)
RUN sudo apt-get -y install openssh-server
RUN echo "root:stratio" | sudo chpasswd
RUN sed -i "/PermitRootLogin without-password/s/^/#/" /etc/ssh/sshd_config
RUN echo "PermitRootLogin yes" >>  /etc/ssh/sshd_config
RUN mkdir ~/.ssh/
ADD ssh/ /tmp/ssh
RUN mv /tmp/ssh/* ~/.ssh
RUN rmdir /tmp/ssh
RUN cat ~/.ssh/id_rsa.pub > ~/.ssh/authorized_keys
RUN chmod -R 0600 ~/.ssh
RUN echo "    StrictHostKeyChecking no" >> /etc/ssh/ssh_config
# curl
RUN apt-get -y install curl vim zip

#Cleansing
RUN apt-get clean
RUN rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
