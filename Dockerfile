# Build
FROM gradle:jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
ARG GPR_USER
ARG GPR_KEY
RUN gradle build --no-daemon

# Package
FROM openjdk:21-jdk
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ARG PROFILE
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${PROFILE}","app.jar"]
