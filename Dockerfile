# ----------- Build Stage -----------
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and download dependencies first (this optimizes caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Now copy the rest of the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# ----------- Run Stage -----------
FROM eclipse-temurin:17-jre-jammy

# Set working directory inside the container
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
