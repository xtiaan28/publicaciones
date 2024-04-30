FROM openjdk:21-ea-24-oracle

WORKDIR /app
COPY target/publicaciones-0.0.1-SNAPSHOT.jar app.jar
COPY Wallet_DB_Prueba /app/oracle_wallet
EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]