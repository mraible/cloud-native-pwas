# Cloud Native Progressive Web Apps with Spring Boot and Angular

**Prerequisites**: Java 8 and Node.js

## Running the Project

Clone this project, start each Spring Boot app, then start the Angular client.

```bash
git clone git@github.com:mraible/cloud-native-pwas.git
cd cloud-native-pwas
cd kolin-reactive # or kotlin-basic
```

To run the client and all the servers, execute `./run.sh`, or execute the [commands in this file](https://github.com/mraible/cloud-native-pwas/blob/master/kotlin-reactive/run.sh) manually.

## Learn More

Watch [Building Robust APIs and Apps with Spring Boot and Angular](https://virtualjug.com/building-robust-apis-and-apps-with-spring-boot-and-angular/) from Virtual JUG, August 2017.

To see how to develop the Spring Boot microservices (with Java), see [Build a Microservices Architecture for Microbrews with Spring Boot](https://developer.okta.com/blog/2017/06/15/build-microservices-architecture-spring-boot)

To see how to develop the Angular client, see [Build Your First Progressive Web Application with Angular and Spring Boot](https://developer.okta.com/blog/2017/05/09/progressive-web-applications-with-angular-and-spring-boot). 

## Security with Okta

To see how to lock things down with Okta, see the `okta` branch and [this PR](https://github.com/mraible/cloud-native-pwas/pull/10).

> **NOTE:** This only works with the `kotlin-basic` project. Okta's Spring Boot Starter [doesn't work with Spring Cloud Gateway](https://github.com/okta/okta-spring-boot/issues/24).