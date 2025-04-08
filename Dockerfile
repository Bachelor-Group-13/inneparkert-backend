FROM maven:3.9.4-eclipse-temurin-21

WORKDIR /app

COPY . .

RUN chmod +x ./mvnw

RUN ./mvnw dependency:resolve

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]
