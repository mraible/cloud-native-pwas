#!/bin/bash

cf d -f eureka-service
cf d -f beer-catalog-service
cf d -f edge-service
cf d -f pwa-client

cf ds -f pwa-eureka

cf delete-orphaned-routes
