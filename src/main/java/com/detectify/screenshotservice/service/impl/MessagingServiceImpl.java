package com.detectify.screenshotservice.service.impl;

import com.detectify.screenshotservice.service.MessagingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessagingServiceImpl implements MessagingService<String> {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String topic, String payload) {
        log.info("Send message to topic [{}]", topic);
        kafkaTemplate.send(topic, payload)
                .addCallback(o -> handleSuccess(topic, payload),
                        o -> handleFailure(topic, payload, o));
    }

    @Override
    public void send(String topic, List<String> payloads) {
        log.info("Send messages to topic [{}]", topic);
        for (String payload : payloads) {
            kafkaTemplate.send(topic, payload)
                    .addCallback(o -> handleSuccess(topic, payload),
                            o -> handleFailure(topic, payload, o));
        }
    }


    private void handleSuccess(String topic, Object payload) {
        log.info("Message {} was sent to the topic {} successfully!", payload, topic);
    }

    private void handleFailure(String topic, Object payload, Throwable ex) {
        String msg = MessageFormat.format("Message {0} was not sent to the topic {1}", payload, topic);
        log.error(msg, ex);
    }
}
