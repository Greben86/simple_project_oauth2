# Родительский образ контейнера с gradle внутри
FROM gradle:jdk21 AS build
LABEL authors="viktor"
COPY . /app
WORKDIR /app
RUN gradle build

# Родительский образ контейнера с java внутри
FROM eclipse-temurin AS run
LABEL authors="viktor"
# Копирование jar-файла в контейнер
COPY --from=build /app/build/libs/demo-0.0.1-SNAPSHOT.jar /app.jar
# Открытие порта 8085
EXPOSE 8085
# Команда, которая будет запущена при старте контейнера java -jar ./app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]