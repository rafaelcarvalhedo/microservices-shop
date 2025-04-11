# Build stage
FROM gradle:8.6.0-jdk17 AS build
WORKDIR /app
COPY . .
# Configurar cache do Gradle
ENV GRADLE_USER_HOME=/app/.gradle
RUN --mount=type=cache,target=/app/.gradle \
    gradle build --no-daemon

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"] 