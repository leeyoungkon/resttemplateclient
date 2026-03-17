FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy project files and build the executable jar
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn
COPY src src
RUN chmod +x mvnw && ./mvnw -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy built Spring Boot jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]