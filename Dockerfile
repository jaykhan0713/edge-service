# ---- Dockerfile (production) ----
# Clean, portable build. Relies only on normal Docker layer caching.
# No BuildKit cache mounts, so it behaves the same in CI and locally.


# when using buildspec, will pass in ecr for base images otherwise will default to below args
ARG BUILD_IMAGE=eclipse-temurin:25-jdk
ARG RUNTIME_IMAGE=eclipse-temurin:25-jre
ARG VERSION

# ---- build stage ----
FROM ${BUILD_IMAGE} AS build
WORKDIR /app

# Copy Gradle wrapper FIRST so layers cache well
COPY gradlew gradlew.bat ./
COPY gradle/wrapper/ ./gradle/wrapper/

# Build scripts (dependency graph inputs)
COPY settings.gradle.kts build.gradle.kts ./

# Prime Gradle dependency resolution.
# This layer stays cached as long as build scripts do not change.
RUN chmod +x gradlew && ./gradlew --no-daemon -x test help

# Sources (change most often)
COPY src ./src

# Build the Spring Boot executable jar (bootJar).
# Gradle config sets bootJar archiveFileName to app.jar.
RUN ./gradlew --no-daemon -x test clean bootJar

# ---- runtime stage ----
FROM ${RUNTIME_IMAGE}
ARG VERSION
ENV VERSION="${VERSION}"
WORKDIR /app

# Copy the single boot jar output (no wildcards).
COPY --from=build /app/build/libs/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
