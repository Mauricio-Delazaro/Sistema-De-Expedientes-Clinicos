# ── Etapa 1: Compilación ────────────────────────────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copiar wrapper y pom primero para aprovechar la caché de dependencias
COPY .mvn/         .mvn/
COPY mvnw          mvnw
COPY pom.xml       pom.xml
RUN chmod +x mvnw && ./mvnw dependency:go-offline -q

# Copiar el código fuente y compilar
COPY src/ src/
RUN ./mvnw package -DskipTests -q

# ── Etapa 2: Imagen de ejecución ─────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/expedientes-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
