FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

ENV PORT 8181

EXPOSE 8181

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

