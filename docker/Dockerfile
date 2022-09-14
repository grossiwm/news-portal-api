FROM openjdk:17-jdk-slim

ARG DD_API_KEY

RUN apt-get -y update && apt-get -y install curl
RUN DD_AGENT_MAJOR_VERSION=7 DD_API_KEY=${DD_API_KEY} DD_SITE="datadoghq.com" bash -c "$(curl -L https://s3.amazonaws.com/dd-agent/scripts/install_script.sh)"
RUN mkdir /var/app
ADD ../portal-0.0.1-SNAPSHOT.jar /var/app
RUN curl -L https://dtdg.co/latest-java-tracer --output /var/app/dd-java-agent.jar
ADD ./entrypoint.sh /opt/startup.sh
RUN chmod +x /opt/startup.sh

ENTRYPOINT ["/bin/bash", "-c", "/opt/startup.sh"]