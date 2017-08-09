#!/bin/bash

# TODO: make sure to copy the application-cloud.properties file into the beer-catalog-service and edge-service's src/main/resources directories.

cp application-cloud.properties ./edge-service/src/main/resources/application-cloud.properties
cp application-cloud.properties ./beer-catalog-service/src/main/resources/application-cloud.properties


start=`pwd`

cd $start && find . -iname pom.xml | xargs -I pom  mvn -DskipTests clean package -f pom
cd $start/beer-catalog-service && ./gradlew bootRepackage

cf s | grep pwa-eureka || cf create-user-provided-service pwa-eureka -p '{"uri":"http://pwa-eureka.cfapps.io"}'

cd $start/client
rm -rf dist
sed -i -e "s|http://localhost:8081|https://pwa-edge.cfapps.io|g" $start/client/src/app/shared/beer/beer.service.ts
npm install && ng build -prod --aot
touch dist/Staticfile

cd $start
cf push

git checkout $start/client
rm $start/client/src/app/shared/beer/beer.service.ts-e
