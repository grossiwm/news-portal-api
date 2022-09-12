FROM openjdk:17-jdk-slim

RUN mkdir /var/app
ADD ./target/portal-0.0.1-SNAPSHOT.jar /var/app

ENTRYPOINT ["java", "-jar", "/var/app/portal-0.0.1-SNAPSHOT.jar"]