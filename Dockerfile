FROM saltatorv-java-env:22

WORKDIR /housefy

COPY housefy.jar housefy.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "housefy.jar"]