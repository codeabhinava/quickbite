package com.example.quickbite.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OTPProducer {

    private static final String TOPIC = "otp-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendOTP(String otp, String userId) {
        String message = "OTP: " + otp + ", User ID: " + userId;
        kafkaTemplate.send(TOPIC, message);
        log.info("Sent OTP:{} for User ID:{} " + message);
    }

}
