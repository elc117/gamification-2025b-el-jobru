# --- Estágio 1: Build (Construção) ---
# Usamos uma imagem que já tem o Maven e o JDK 21
FROM maven:3.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o pom.xml primeiro. Isso aproveita o cache do Docker.
COPY pom.xml .

# Copia o resto do código-fonte
COPY src ./src

# Roda o build do Maven (usando o shade plugin) para criar o "fat jar"
# Isso criará o JAR em /app/target/el-jobru-1.0.jar
RUN mvn clean package -DskipTests

# --- Estágio 2: Run (Execução) ---
# Usamos uma imagem JRE (só para rodar) enxuta com Java 21
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia apenas o "fat jar" do estágio de build para a imagem final
# Renomeamos para app.jar por simplicidade
COPY --from=builder /app/target/el-jobru-1.0.jar /app/app.jar

# Expõe a porta que o Render espera para serviços Docker
EXPOSE 8080

# Comando para iniciar a aplicação quando o container subir
CMD ["java", "-jar", "/app/app.jar"]