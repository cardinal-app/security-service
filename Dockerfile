# Build
FROM gradle:jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Package
FROM openjdk:21-jdk
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ARG profile
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${profile}","app.jar"]
