# Dockerfile for building Spring Boot microservice
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/challenge-0.0.1-SNAPSHOT.jar /app/challenge-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/challenge-0.0.1-SNAPSHOT.jar"]
