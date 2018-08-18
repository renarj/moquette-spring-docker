FROM java:8
VOLUME /tmp

ARG JAR_FILE
ADD ${JAR_FILE} mqtt-svc.jar

RUN bash -c 'touch /mqtt-svc.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mqtt-svc.jar"]

EXPOSE 1883