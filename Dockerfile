FROM openjdk:11-jre-slim

# Add the application's jar to the container
COPY target/mankala*.jar /app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]