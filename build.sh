#!/bin/bash
echo $PWD
cd user-commons-service
./mvnw clean install
cd ..
cd eureka-server
./mvnw clean install
cd ..
cd gateway-service
./mvnw clean install
cd ..
cd oauth-service
./mvnw clean install
cd ..
cd order-detail-service
./mvnw clean install
cd ..
cd order-detail-service
order-service
./mvnw clean install
cd ..
cd order-service
./mvnw clean install
cd ..
cd payment-service
./mvnw clean install
cd ..
cd user-service
./mvnw clean install
cd ..
docker-compose up -d