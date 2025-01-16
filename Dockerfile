FROM openjdk:17

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} brainpix.jar

ARG ENV_KEY
ENV APP_ENV_KEY=${ENV_KEY}

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev", "--jasypt.encryptor.password=${APP_ENV_KEY}"]