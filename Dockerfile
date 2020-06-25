FROM openjdk:8
EXPOSE 9091
ADD target/my-library-challenge.jar my-library-challenge.jar
ENTRYPOINT ["java","-jar","/my-library-challenge.jar"]

