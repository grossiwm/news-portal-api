FROM openjdk:17-jdk-slim

ARG DD_API_KEY
RUN apt-get -y update && apt-get -y install curl
RUN DD_AGENT_MAJOR_VERSION=7 DD_API_KEY=${DD_API_KEY} DD_SITE="datadoghq.com" bash -c "$(curl -L https://s3.amazonaws.com/dd-agent/scripts/install_script.sh)"
RUN mkdir /var/app
ADD ./portal-0.0.1-SNAPSHOT.jar /var/app
RUN curl -L https://dtdg.co/latest-java-tracer --output /var/app/dd-java-agent.jar

ENTRYPOINT ["java", "-javaagent:/var/app/dd-java-agent.jar", "-Ddd.service=news-portal-api", "-Ddd.env=${ACTIVE_PROFILE}", "-Ddd.logs.injection=true", "-Dsendgrid.verifiedSenderIdentity=${SEND_GRID_IDENTITY}", "-Dsendgrid.apiKey=${SEND_GRID_API_KEY}", "-Dspring.datasource.url=${DB_URL}", "-Dspring.datasource.username=${DB_USERNAME}", "-Dspring.datasource.password=${DB_PASSWORD}", "-Dspring.rabbitmq.host=${RABBIT_HOST}", "-Dspring.rabbitmq.port=${RABBIT_PORT}", "-Dspring.rabbitmq.username=${RABBIT_USERNAME}", "-Dspring.rabbitmq.password=${RABBIT_PASSWORD}", "-Dspring.rabbitmq.ssl.enabled=${RABBIT_SSL_ENABLED}", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-jar", "/var/app/portal-0.0.1-SNAPSHOT.jar"]