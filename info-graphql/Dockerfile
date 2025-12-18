FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/info-graphql-1.0.0.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
