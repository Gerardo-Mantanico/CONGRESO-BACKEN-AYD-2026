# Etapa 1: Builder
FROM gradle:8.4-jdk21 AS builder
WORKDIR /app

# Copiar archivos de proyecto
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src

# Dar permisos al wrapper
RUN chmod +x ./gradlew

# Construir el JAR (ignora tests para acelerar)
RUN ./gradlew clean bootJar -x test --no-daemon

# Debug opcional para ver que se generó el JAR
RUN ls -la /app/build/libs

# Etapa 2: Runner
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar JAR construido desde la etapa builder
COPY --from=builder /app/build/libs/Congreso-0.0.1-SNAPSHOT.jar app.jar

# Exponer puerto de la app
EXPOSE 8080

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
