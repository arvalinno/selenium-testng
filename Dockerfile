# Use an official Java 17 runtime
FROM eclipse-temurin:17-jdk

# Set working directory to match your project
WORKDIR /selenium-testng

# Copy the built JAR and name it selenium-testng.jar
COPY target/*.jar selenium-testng.jar

# Expose the port your Spring Boot API listens on
EXPOSE 8080

# Launch the application
ENTRYPOINT ["java", "-jar", "selenium-testng.jar"]