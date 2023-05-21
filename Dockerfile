# Use the base image with JDK 17 Alpine
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file to the application directory
COPY target/*.jar app.jar


# Exposez le port 8080 pour permettre l'accès à l'application
EXPOSE 8080

# Run the Java application when the container starts
CMD ["java", "-jar", "/app/app.jar"]



# CMD :  docker build -t manage-team-test .
# CMD :  docker run -p 8080:8080 manage-team-test
