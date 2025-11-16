# Estágio 1: Build (Usando Maven com Temurin JDK 21)
FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app

# Cachear dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código e compilar
COPY src ./src
RUN mvn clean install -DskipTests

# Estágio 2: Run (Usando Temurin JRE 21 - a alternativa ao openjdk)
# ESTA É A LINHA QUE MUDOU:
FROM eclipse-temurin:21-jre-alpine

EXPOSE 8080

# Copiar o JAR compilado
COPY --from=build /app/target/el-jobru-1.0.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]