FROM openjdk:11
ARG JAR_FILE=build/libs/crypto-server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-Dspring.profiles.active=live", "-jar","/app.jar"]