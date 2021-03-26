FROM adoptopenjdk/openjdk16
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
ARG JAR_FILE=*.jar
COPY spring-boot-server/mykid-rest-adapter/target/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]