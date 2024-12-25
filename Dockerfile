FROM gradle:8.7.0-jdk21

WORKDIR /app

COPY / .

RUN gradle installDist

EXPOSE 8080

CMD ./build/install/app/bin/app --spring.profiles.active=production