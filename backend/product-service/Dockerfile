# Use Java 21 base image
FROM eclipse-temurin:21-jre-jammy

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/*.jar app.jar

# Expose port
EXPOSE 8070

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]