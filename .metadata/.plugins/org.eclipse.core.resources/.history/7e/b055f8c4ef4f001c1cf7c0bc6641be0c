FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD ./target/proviHack-0.0.1-SNAPSHOT.jar provihack.jar
ENTRYPOINT ["java","-jar","/provihack.jar"]
