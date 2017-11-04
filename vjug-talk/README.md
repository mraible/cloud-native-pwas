# Cloud Native Progressive Web Apps with Spring Boot and Angular

**Prerequisites**: Java 8 and Node.js

To run the client and all the servers, execute `./run.sh`, or execute the [commands in this file](https://github.com/mraible/cloud-native-pwas/blob/master/run.sh) manually.

To see how to develop the Spring Boot microservices, see [Build a Microservices Architecture for Microbrews with Spring Boot](https://developer.okta.com/blog/2017/06/15/build-microservices-architecture-spring-boot)

To see how to develop the Angular client, see [Build Your First Progressive Web Application with Angular and Spring Boot](https://developer.okta.com/blog/2017/05/09/progressive-web-applications-with-angular-and-spring-boot). 

## Security with Okta
To see how to lock things down with Okta, see the `okta` branch and learn how to do it by reading [Secure a Spring Microservices Architecture with Spring Security, JWTs, Juiser, and Okta](https://developer.okta.com/blog/2017/08/08/secure-spring-microservices). 

> **NOTE:** The code in this repository doesn't add security to the `beer-catalog-service`, it only uses the `stormpath-zuul-spring-cloud-starter` on the `edge-service`.
