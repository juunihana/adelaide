FROM eclipse-temurin:22-jre-ubi9-minimal
WORKDIR /app
COPY target/*.jar /app/adelaide.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=test", "-jar", "/app/adelaide.jar"]