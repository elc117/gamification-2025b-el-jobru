FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install open-jdk-21-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY --from=build /target/el-jobru-1.0.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]