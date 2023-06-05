FROM openjdk:17
VOLUME /tmp
ADD ./target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java", "-jar", "user-service.jar"]


