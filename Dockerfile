FROM openjdk:17
EXPOSE 8000 8080
ADD target/webmToTelegram-0.0.1-SNAPSHOT.jar webmToTelegram-0.0.1-SNAPSHOT.jar
ADD entrypoint.sh entrypoint.sh
ENTRYPOINT ["sh","/entrypoint.sh"]