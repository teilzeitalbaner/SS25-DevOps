# Build Image - referencable by build
FROM maven:3.9.7-eclipse-temurin-17-focal AS build
WORKDIR /workspace

# COPY Parent POM
COPY pom.xml .
# COPY Module POMs
COPY storage/pom.xml storage/pom.xml
COPY api/pom.xml api/pom.xml
RUN mvn -B -ntp -q -DskipTests dependency:go-offline

# COPY sources
COPY storage/src storage/src
COPY api/src api/src
RUN mvn -B -ntp -DskipTests -pl api -am package

# Final image
FROM eclipse-temurin:17-jre-ubi9-minimal

# Copy build results
WORKDIR /app
# CR: Copy von lib funktioniert nur, wenn dependency go offline aufgerufen wurde
#COPY --from=build ${DEPENDENCY}/lib ./lib
#COPY --from=build ${DEPENDENCY}/taskvault-api.jar .

COPY --from=build /workspace/api/target/lib ./lib
COPY --from=build /workspace/api/target/taskvault-api.jar taskvault-api.jar

# Specify listening port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "taskvault-api.jar"]