FROM openjdk:11.0.8
RUN apt-get update && apt-get install -y maven
COPY ./backend /project/
RUN  cd /project && mvn package

#run the spring boot application
ENTRYPOINT ["java", "/project/target/boxinator-0.0.1-SNAPSHOT.jar"]