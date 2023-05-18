FROM gradle:7.5.1-jdk18-alpine AS builder
COPY . /app
WORKDIR /app
RUN gradle clean bootJar --no-daemon

FROM openjdk:18-alpine
COPY --from=builder ./app/build/libs/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
