From eclipse/temurin:21-jre

WORKDIR /app

COPY target/spring-boot-0.0.1-SNAPSHOT.jar /app/spring-boot-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "spring-boot-0.0.1-SNAPSHOT.jar"]