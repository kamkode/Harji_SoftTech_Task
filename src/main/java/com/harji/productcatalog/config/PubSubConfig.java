package com.harji.productcatalog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration for GCP Pub/Sub.
 */
@Configuration
@Profile("pubsub")
public class PubSubConfig {
}
