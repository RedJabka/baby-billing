FROM openjdk:17-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install -y curl

COPY target/*.jar  /app/app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]