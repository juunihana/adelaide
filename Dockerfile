FROM eclipse-temurin:22-jre-ubi9-minimal
WORKDIR /app
COPY target/*.jar /app/adelaide.jar
EXPOSE 9001
ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-jar", "/app/adelaide.jar"]