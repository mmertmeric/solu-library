# --- 1. AŞAMA: İNŞAAT (BUILD) ---
# Maven yüklü bir Linux makinesi getir ve projeyi derle
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- 2. AŞAMA: ÇALIŞTIRMA (RUN) ---
# Sadece Java yüklü hafif bir makine getir
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# İlk aşamada üretilen .jar dosyasını buraya al
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Uygulamayı başlat
ENTRYPOINT ["java", "-jar", "app.jar"]