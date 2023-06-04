FROM openjdk:17 as build

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
FROM openjdk:17
VOLUME /tmp
EXPOSE 8761
COPY --from=build ./target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar
ENTRYPOINT [ "java", "-jar", "/eureka-server.jar" ]