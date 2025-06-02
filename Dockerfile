# Use OpenJDK 17 slim image
FROM openjdk:17-jdk-slim

# Add metadata
LABEL build_date="2025-05-14"

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/*.jar app.jar

# Copy the uploads directory (if your app reads/writes to it)
COPY uploads/ uploads/

# Expose Spring Boot default port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
