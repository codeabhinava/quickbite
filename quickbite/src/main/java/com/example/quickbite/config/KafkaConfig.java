package com.example.quickbite.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaConfig {

    @Bean
    public NewTopic otpTopic() {
        return TopicBuilder.name("otp-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic emailTopic() {
        return TopicBuilder.name("email-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
