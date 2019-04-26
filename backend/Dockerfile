FROM java:8-jdk-alpine
ARG JAR_FILE
RUN mkdir -p /var/lib/railway-routing-service
ADD ${JAR_FILE} /app/railway-routing-service-1.0.0.jar
ENTRYPOINT ["java", "-jar","/app/railway-routing-service-1.0.0.jar"]