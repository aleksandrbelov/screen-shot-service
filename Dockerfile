FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

FROM openjdk:8-jre-slim

ARG SERVICE_NAME=screen-shot-service
ARG JAR_PATH=/workspace/app/target/*.jar

ENV DEBUG_ENABLED false
ENV DEBUG_PORT 5005
ENV SERVICE_NAME ${SERVICE_NAME}

WORKDIR /opt/detectify

COPY assets/startup.sh bin/
RUN chmod 755 bin/startup.sh

COPY --from=build ${JAR_PATH} lib/${SERVICE_NAME}.jar

ENTRYPOINT ["./bin/startup.sh"]
