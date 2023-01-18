#https://levelup.gitconnected.com/how-to-integrate-elastic-apm-java-agent-with-spring-boot-7ce8388a206e
FROM docker.io/library/amazoncorretto:11-alpine3.15
EXPOSE 8062

VOLUME ["/mylogs"]

RUN mkdir -p /opt/app && \
    wget -O /opt/app/apm-agent.jar https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/1.35.0/elastic-apm-agent-1.35.0.jar

WORKDIR /opt/app

ADD ./target/*.jar /opt/app/app.jar
ADD ./target/classes/elasticapm.properties .

RUN chmod -R uog+x /opt/app

ENTRYPOINT ["java","-javaagent:apm-agent.jar", "-jar","app.jar"]
