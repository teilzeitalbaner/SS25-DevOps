# Build Image - referencable by build
FROM maven:3.9.7-eclipse-temurin-17-focal as build
WORKDIR /workspace/app

# COPY Module POMs
COPY storage/pom.xml storage/pom.xml
COPY api/pom.xml api/pom.xml

# COPY Parent POM
COPY pom.xml pom.xml
# Execute maven dependencies
RUN mvn dependency:go-offline
# todo: xander hat die hier drin / einkommentiert
# CR: Ich habe die beim hinzufügen der API hinzugefügt damit Bibliotheken herunter geladen werden.
# Damit wir das Docker Image bauen können.
# Keep in mind. Das muss auch in der pom.xml der API hinzugefügt werden, sonst ist der Befehl nicht bekannt

# COPY sources
COPY storage/src storage/src
COPY api/src api/src

# Jar packen
RUN mvn package -DskipTests

# Final image
FROM eclipse-temurin:17-jre-ubi9-minimal
ARG DEPENDENCY=/workspace/app/api/target

# Copy build results
WORKDIR /app
# CR: Copy von lib funktioniert nur, wenn dependency go offline aufgerufen wurde
COPY --from=build ${DEPENDENCY}/lib ./lib
COPY --from=build ${DEPENDENCY}/taskvault-api.jar .

# Specify listening port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "taskvault-api.jar"]