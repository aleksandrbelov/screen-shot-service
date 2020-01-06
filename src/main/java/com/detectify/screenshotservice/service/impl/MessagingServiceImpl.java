package com.detectify.screenshotservice.service.impl;

import com.detectify.screenshotservice.service.MessagingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessagingServiceImpl implements MessagingService<String> {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String topic, String payload) {
        log.info("Send message to topic [{}]", topic);
        kafkaTemplate.send(topic, payload);
    }

    @Override
    public void send(String topic, List<String> payloads) {
        log.info("Send messages to topic [{}]", topic);
        for (String payload: payloads) {
            kafkaTemplate.send(topic, payload);
        }
    }
}
