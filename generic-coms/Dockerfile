# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Kaynak kodu komple kopyalayalım (go-offline yok; doğrudan paketleyeceğiz)
COPY . .

# Maven çıktısını sadeleştir, testleri atla, aktarım progress kapalı
RUN mvn -B -DskipTests -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn \
       -Dorg.slf4j.simpleLogger.showDateTime=true \
       -Dorg.slf4j.simpleLogger.log.org.eclipse.aether.internal.impl.DefaultUpdateCheckManager=error \
       --no-transfer-progress package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/generic-coms-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
