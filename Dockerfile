# Build (Usando a imagem Maven oficial)
# Esta imagem JÁ TEM Maven E JDK 21.
FROM maven:3-eclipse-temurin-21 AS build

WORKDIR /app

# Copia os arquivos do projeto (pom.xml primeiro para cachear dependências)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do código-fonte
COPY src ./src

# Roda o build (pulando os testes para um build mais rápido no Docker)
RUN mvn clean install -DskipTests

# Run (Usando a imagem JRE, que é menor)
# JRE (Java Runtime Environment) é o suficiente, não precisa do JDK
FROM openjdk:21-jre-alpine

# Expõe a porta que o Javalin vai usar
EXPOSE 8080

# Copia APENAS o JAR compilado do estágio 'build'
# Ajuste o nome do JAR se for diferente
COPY --from=build /app/target/el-jobru-1.0.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT [ "java", "-jar", "app.jar" ]