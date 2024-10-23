FROM openjdk:17-jdk-slim

WORKDIR /ufund-api

COPY src/ufund-api/target/ufund-api-0.0.1-SNAPSHOT.jar /ufund-api/UFUND.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/ufund-api/UFUND.jar"]