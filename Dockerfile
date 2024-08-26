FROM maven:3.9.8

WORKDIR /code

COPY . /code

RUN mvn clean install -DskipTests=true

ARG DB_USERNAME
ARG DB_PASSWORD
ARG DB_HOST
ARG DB_NAME

ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV DB_HOST=${DB_HOST}
ENV DB_NAME=${DB_NAME}

EXPOSE 8080

CMD ["java","-jar", "/code/target/JavaWebService-1.0-SNAPSHOT.jar", "server", "/code/config.yml"]