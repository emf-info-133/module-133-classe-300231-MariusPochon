FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY client1/target/client1-0.0.1-SNAPSHOT.jar module133classe300231mariuspochon.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar module133classe300231mariuspochon.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar module133classe300231mariuspochon.jar
