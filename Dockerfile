FROM openjdk:17
EXPOSE 24493
EXPOSE 8080
WORKDIR /app
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:24493
ADD target/webmToTelegram-0.0.1-SNAPSHOT.jar webmToTelegram-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "webmToTelegram-0.0.1-SNAPSHOT.jar"]