FROM maven:3.6.3-jdk-11 as builder
RUN mkdir /app
COPY . /app
WORKDIR /app
RUN mvn clean install

FROM openjdk:11-jdk
COPY --from=builder /app/target/*.jar /app/app.jar
COPY --from=builder /app/src/main/resources/*.json /app/src/main/resources/
COPY --from=builder /app/src/main/resources/testAvas/*.jpg /app/src/main/resources/testAvas/
HEALTHCHECK --interval=5s --timeout=2s --retries=12 --start-period=320s \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]