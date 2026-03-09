package com.example.quickbite.producer;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "email-topic";

    public void sendEmailConfirmation(String email, UUID token) {
        String message = "Email: " + email + ", Confirmation Token: " + token;
        kafkaTemplate.send(TOPIC, token.toString());
        log.info("Sent email confirmation for Email:{} with Token:{} " + message);
    }
}
