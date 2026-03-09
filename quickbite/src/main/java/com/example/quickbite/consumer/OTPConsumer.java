package com.example.quickbite.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OTPConsumer {

    @KafkaListener(topics = "otp-topic", groupId = "otp-group")
    public void consumeOTP(String message) {
        log.info("Received OTP:{} " + message);
    }
}
