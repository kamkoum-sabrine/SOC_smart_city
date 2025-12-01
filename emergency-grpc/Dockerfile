# Stage 1: Build avec Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run avec JRE
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/emergency-grpc-1.0-SNAPSHOT.jar app.jar
EXPOSE 8083 9090
ENTRYPOINT ["java", "-jar", "app.jar"]