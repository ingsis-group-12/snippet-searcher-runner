FROM gradle:latest as build

ARG USERNAME
ENV GITHUB_ACTOR ${USERNAME}

ARG TOKEN
ENV GITHUB_TOKEN ${TOKEN}

COPY  . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle assemble --warning-mode all
FROM openjdk:17-jdk-slim
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production","/app/spring-boot-application.jar"]
