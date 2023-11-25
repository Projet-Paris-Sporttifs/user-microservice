FROM eclipse-temurin:21-jre

WORKDIR /app

EXPOSE 8181

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

