FROM java:8

EXPOSE 8080

ADD /target/event-log*.jar event-log.jar

ENTRYPOINT ["java", "-jar", "event-log.jar"]