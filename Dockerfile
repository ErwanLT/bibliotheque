# Étape 1: Construction de l'application avec Maven
FROM maven:3.9.4-eclipse-temurin-21 AS builder

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de configuration Maven et le code source dans le conteneur
COPY pom.xml .
COPY src ./src

# Construire l'application
RUN mvn clean package -Pproduction

# Étape 2: Image finale pour exécuter l'application
FROM eclipse-temurin:21-jdk-alpine

# Définir le répertoire de travail dans le conteneur final
WORKDIR /app

# Copier le fichier JAR de l'étape précédente
COPY --from=builder /app/target/bibliotheque-0.0.1-SNAPSHOT.jar /app/application.jar

# Exposer le port 8080
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
