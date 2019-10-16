FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/apigateway-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]