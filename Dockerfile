FROM openjdk:13
WORKDIR /usr/app
COPY ./target/fashionblog-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java","-jar","fashionblog-0.0.1-SNAPSHOT.jar"]