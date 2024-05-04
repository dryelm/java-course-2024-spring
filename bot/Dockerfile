FROM openjdk:21

COPY ./target/bot.jar ./app/bot.jar

ENV TOKEN=${TOKEN}

EXPOSE 8090 8091

ENTRYPOINT ["java", "-jar", "./app/bot.jar"]
