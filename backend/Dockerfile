FROM java:8-jdk-alpine
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
RUN mkdir -p /var/lib/railway-routing-service
ENTRYPOINT ["java","-cp","app:app/lib/*","com.mettyoung.railwayroutingservice.RailwayRoutingServiceApplication"]