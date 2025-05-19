#!/bin/bash

# Start the application with RabbitMQ profile
export SPRING_PROFILES_ACTIVE=rabbitmq
export APP_EVENT_PUBLISHER=rabbitmq
mvn spring-boot:run
