FROM openjdk:21

COPY ./target/scrapper.jar ./app/scrapper.jar

EXPOSE 8080 8081

ENTRYPOINT ["java", "-jar", "./app/scrapper.jar"]
