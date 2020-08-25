FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD target/collection-eureka-server-0.0.1-SNAPSHOT.jar app.jar
#RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -D","-jar","/app2.jar"]
EXPOSE 8761