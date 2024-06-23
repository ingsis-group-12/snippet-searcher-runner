FROM gradle:latest as build

ARG USERNAME
ENV GITHUB_ACTOR ${USERNAME}

ARG TOKEN
ENV GITHUB_TOKEN ${TOKEN}

COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle assemble

FROM openjdk:17-jdk-alpine
EXPOSE 8080

RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

ARG NEW_RELIC_LICENSE_KEY
ENV NEW_RELIC_LICENSE_KEY=$NEW_RELIC_LICENSE_KEY

COPY newrelic/newrelic.jar /app/newrelic.jar
COPY newrelic/newrelic.yml /app/newrelic.yml
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production","-javaagent:/app/newrelic.jar","/app/spring-boot-application.jar"]
