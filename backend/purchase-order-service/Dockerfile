## Use an official OpenJDK runtime as a parent image
#FROM openjdk:21-jdk-slim
#
## The application's jar file (Make sure you run 'mvn clean package' first)
#ARG JAR_FILE=target/*.jar
#
## Add the application's jar to the container
#COPY ${JAR_FILE} app.jar
#
## Run the jar file
#ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8071
ENTRYPOINT ["java", "-jar", "app.jar"]