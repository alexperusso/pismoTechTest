FROM docker.io/library/openjdk:15.0.2
EXPOSE 8062
ADD ./target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
