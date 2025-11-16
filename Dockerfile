# Estágio 1: Build (Usando Maven com Temurin JDK 21)
FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app

# Cachear dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código e compilar
COPY src ./src
RUN mvn clean install -DskipTests

# *** NOVO PASSO ***
# Copiar dependências para uma pasta 'lib'
RUN mvn dependency:copy-dependencies -DoutputDirectory=target/lib

# Estágio 2: Run (Usando Temurin JRE 21 - a alternativa ao openjdk)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
EXPOSE 8080

# Copiar o JAR fino (só o seu código)
COPY --from=build /app/target/el-jobru-1.0.jar app.jar

# Copiar todas as dependências (incluindo postgresql.jar)
COPY --from=build /app/target/lib ./lib

# *** ENTRYPOINT MODIFICADO ***
# Usamos -cp (classpath) para incluir o app.jar e tudo na pasta lib/
# Substitua 'com.el_jobru.MainApplication' se o nome da sua classe principal for outro
ENTRYPOINT [ "java", "-cp", "app.jar:lib/*", "com.el_jobru.MainApplication" ]