# CusChallenge

Sample backend applciation for shopping cart based on *Spring boot* using microservice architecture.

## Directory Structure
```bash
├── eureka-server
├── gateway-service
├── oauth-service
├── order-detail-service
├── order-service
├── payment-service
├── product-service
├── user-commons-service
├── build.sh
├── docker-compose.yml
```



#### eureka-server 
This directory contains the service discovery microservice.

#### gateway 
Contains the gateway microservice and also provides security for the other microservices.

#### oauth-service
Contains the oauth microservice for token generation and user authentication.

#### order-detail-service
Contains order-details-service that provides info on every product purchased.

#### order-service
Contains order microservice that deals with order CRUD operations.

#### payment-service
Contains the payment microservice for dealing with payments.

#### product-service
Contains the product microservice that provides information and details about products.

#### user-commnons-service
Contains user library that is used in different microservices.


### build.sh
script for building the projects and run them on containers.


### docker-compose.yml
docker-compose file to run all microservices.

### Requirements

* jdk 17
* docker
* docker-compose

### Running  the application
To run the applciation there is a build.sh file, you need to make it executable in order to run it. Builds all the projects and then deploys them using docker-compose.

### Consuming APIs

* Main entry point is on http://localhost:8090/
* All microservices are protected except for products on http://localhost:8090/api/products
* Order details entry point: http://localhost:8090/api/order-details
* Order entry point: http://localhost:8090/api/orders
* Payment details entry point: http://localhost:8090/api/payments
* User entrypoint: http://localhost:8090/api/users/

* Obtain token to make autorize calls to microservices, you can use postman to do the call, on Authorization tab, choose Basic Auth(**Username:** frontendapp, **Password:** 12345). On Body tab x-www-form-urlencoded option, **username:** ivan, **password:** 12345, **grant_type:** password : POST call to http://localhost:8090/api/oauth/token
