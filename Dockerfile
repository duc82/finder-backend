FROM maven:3-openjdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:22-jdk-slim
COPY --from=build /target/*.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java","-jar","/app.jar"]