FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw && ./mvnw dependency:go-offline

COPY src src

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=build /app/target/memoria-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar"]
