# --- Build Stage ---
# Use a standard Maven and Java 17 image to build the project
FROM maven:3.9-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the working directory
COPY . .

# Run the Maven build command to create the JAR file
RUN mvn clean install

# --- Run Stage ---
# Use a lightweight Java 17 image to run the application
FROM eclipse-temurin:17-jre-alpine

# Set the working directory
WORKDIR /app

# Copy only the built JAR file from the 'build' stage
COPY --from=build /app/target/quiz-backend-0.0.1-SNAPSHOT.jar app.jar

# The command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]