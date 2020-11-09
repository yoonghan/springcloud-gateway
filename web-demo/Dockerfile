FROM balenalib/raspberry-pi2-openjdk:8-jdk

WORKDIR /opt/app

ARG JAR_FILE=build/libs/web-demo-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
