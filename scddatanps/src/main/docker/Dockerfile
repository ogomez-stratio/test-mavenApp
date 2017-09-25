FROM frolvlad/alpine-oraclejdk8:slim

VOLUME /tmp

ADD scddatanps-10.0.7.jar scddatanps.jar

RUN sh -c 'touch /scddatanps.jar'

ENV JAVA_OPTS="-Xmx512m -Xms256m"

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /scddatanps.jar" ]

