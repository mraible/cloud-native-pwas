#!/bin/bash


start=`pwd`
echo start directory: $start

# build the projects
# cd $start && find . -iname pom.xml | xargs -I pom  mvn -DskipTests clean package -f pom
cd $start/beer-catalog-service && ./gradlew bootRepackage

# push to the cloud
cf s | grep pwa-eureka || cf create-user-provided-service pwa-eureka -p '{"uri":"http://pwa-eureka.cfapps.io"}'
cd $start
cf push
