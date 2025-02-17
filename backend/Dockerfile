FROM openjdk:17-alpine
WORKDIR /app
COPY target/pizza.jar pizza.jar
EXPOSE 9111
ENTRYPOINT [ "java", "-jar", "pizza.jar" ]