# Start from an official Java image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built jar file (you should build it first)
COPY target/*.jar app.jar

# Expose port (default Spring Boot port)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
