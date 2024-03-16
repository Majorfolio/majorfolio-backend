FROM amazoncorretto:17
EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV JAVA_OPTS="-Xms4g -Xmx4g"
ENV TZ Asia/Seoul
ENTRYPOINT ["java","-jar","/app.jar"]
