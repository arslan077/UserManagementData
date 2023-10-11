FROM openjdk:17
EXPOSE 8080
ADD target/usermanagement.jar usermanagement.jar
ENTRYPOINT ["java" ,"-jar" ,"/usermanagement.jar"]