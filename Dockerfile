FROM openjdk:11-jdk
ARG JAR_FILE=./build/libs/crypto-server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} crypto-server.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-Dspring.profiles.active=live", "-jar","/crypto-server.jar"]