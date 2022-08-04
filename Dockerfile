FROM maven:3.8.6-eclipse-temurin-17 as builder

ENV HOME=/workspace

WORKDIR /workspace

COPY pom.xml $HOME

RUN mvn dependency:resolve

COPY .  $HOME

RUN mvn package -DskipTests -Djacoco.skip

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /workspace/target/alura-bank-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
