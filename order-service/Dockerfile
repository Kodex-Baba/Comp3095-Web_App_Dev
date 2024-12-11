#------------
#Build Stage
#---------------

FROM gradle:8-jdk22-alpine AS builder

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src


RUN gradle build -x test


#-------------
# Package
#-------------
FROM openjdk:22-jdk

RUN mkdir /app

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/order-service.jar

# Set PostgreSQL environment variables
ENV POSTGRES_USER=admin \
    POSTGRES_PASSWORD=password \
    POSTGRES_DB=order-service

#ENV MONGO_DB_USERNAME=admin \
#    MONGO_DB_PWD=password

EXPOSE 8087

ENTRYPOINT ["java", "-jar", "/app/order-service.jar" ]

