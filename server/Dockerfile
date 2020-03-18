FROM maven:3.6.3-jdk-8 as builder
RUN mkdir /app
COPY . /app
WORKDIR /app

RUN mvn clean install