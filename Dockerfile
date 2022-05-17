FROM openjdk:17-jdk
EXPOSE 8080 8000
ADD target/webmToTelegram-0.0.1-SNAPSHOT.jar webmToTelegram-0.0.1-SNAPSHOT.jar
ADD entrypoint.sh entrypoint.sh
ENTRYPOINT ["sh","/entrypoint.sh"]