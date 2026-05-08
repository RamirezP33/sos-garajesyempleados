FROM ubuntu:24.04

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
RUN apt-get install maven -y

WORKDIR /app

COPY . .

RUN mvn clean package -Dmaven.test.skip=true

EXPOSE 8080

CMD ["java", "-jar", "target/garajesyempleados-0.0.1-SNAPSHOT.jar"]