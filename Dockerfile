# Usa una imagen oficial de Java con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Crea directorio en el contenedor
WORKDIR /app

# Copia el contenido del proyecto
COPY . .

# Compila el proyecto y genera el JAR
RUN mvn clean package -DskipTests

# Imagen final más ligera solo con JRE
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto 8080 (Spring Boot)
EXPOSE 8080

# Comando para ejecutar el JAR
CMD ["java", "-jar", "app.jar"]
