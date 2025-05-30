# Etapa 1: Build usando Maven e JDK 17
FROM maven:3.8.7-eclipse-temurin-17 AS build

# Diretório dentro do container onde vamos trabalhar
WORKDIR /app

# Copia os arquivos de configuração primeiro (para otimizar cache do Docker)
COPY pom.xml .

# Baixa as dependências (cache)
RUN mvn dependency:go-offline

# Copia o código-fonte
COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true

# Etapa 2: Imagem menor para rodar o jar (JRE leve Alpine com Java 17)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia o jar gerado da etapa build para essa imagem menor
COPY --from=build /app/target/*.jar app.jar

# Expor a porta padrão do Spring Boot (você pode mudar, se quiser)
EXPOSE 8080

# Variável de ambiente para rodar na porta que o Render definir
ENV PORT 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]
