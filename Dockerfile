FROM openjdk:17-jdk-slim

RUN mkdir /var/app
ADD ./portal-0.0.1-SNAPSHOT.jar /var/app

ENTRYPOINT ["java", "-Dsendgrid.verifiedSenderIdentity=${SEND_GRID_IDENTITY}", "-Dsendgrid.apiKey=${SEND_GRID_API_KEY}", "-Dspring.datasource.url=${DB_URL}",   "-Dspring.datasource.username=${DB_USERNAME}",    "-Dspring.datasource.password=${DB_PASSWORD}",    "-Dspring.rabbitmq.host=${RABBIT_HOST}", "-Dspring.rabbitmq.port=${RABBIT_PORT}", "-Dspring.rabbitmq.username=${RABBIT_USERNAME}", "-Dspring.rabbitmq.password=${RABBIT_PASSWORD}", "-Dspring.rabbitmq.ssl.enabled=${RABBIT_SSL_ENABLED}", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-jar", "/var/app/portal-0.0.1-SNAPSHOT.jar"]