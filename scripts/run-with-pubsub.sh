#!/bin/bash

# Start the application with Pub/Sub profile
export SPRING_PROFILES_ACTIVE=pubsub
mvn spring-boot:run
