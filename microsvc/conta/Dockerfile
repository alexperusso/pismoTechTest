FROM docker.io/library/openjdk:15.0.2
EXPOSE 8061
VOLUME ["/mylogs"]
ADD ./target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
