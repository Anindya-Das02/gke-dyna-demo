FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY target/gke-dyna-demo-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
