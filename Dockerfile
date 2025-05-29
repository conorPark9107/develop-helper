FROM gradle:8.14.1-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test --no-daemon

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
