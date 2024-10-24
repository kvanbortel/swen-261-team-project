FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/ufund-api-0.0.1-SNAPSHOT.jar /app/ufund-api.jar

COPY data /app/data

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/ufund-api.jar"]