FROM openjdk:17
WORKDIR /app
COPY target/telegram-bot-springboot-0.0.1-SNAPSHOT.jar /app/telegram-bot-springboot.jar
EXPOSE 8080
CMD ["java", "-jar", "telegram-bot-springboot.jar"]