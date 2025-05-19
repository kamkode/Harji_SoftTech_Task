package com.harji.productcatalog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration for RabbitMQ.
 * In a real implementation, this would configure the RabbitMQ client.
 */
@Configuration
@Profile("rabbitmq")
public class RabbitMQConfig {
    /**
     *  You could add beans for RabbitMQ connection factory, template, etc.
     * No specific configuration needed for the mock implementation
     *  This is a placeholder for actual RabbitMQ configuration
     *  In a real implementation, this would configure the RabbitMQ client
     */

}
