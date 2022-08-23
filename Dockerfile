FROM openjdk:11
COPY build/libs/crypto-server-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-Dspring.profiles.active=live", "-jar","/app.jar"]