FROM openjdk:18-alpine
COPY build/libs/*.jar phoneShop.jar
ENTRYPOINT ["java", "-jar", "/phoneShop.jar"]
