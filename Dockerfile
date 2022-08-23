FROM openjdk:11-jdk AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY seetings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

FROM openjdk:11-jdk
COPY --from=builder build/libs/crypto-server-0.0.1-SNAPSHOT.jar crypto-server.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-Dspring.profiles.active=live", "-jar","/crypto-server.jar"]