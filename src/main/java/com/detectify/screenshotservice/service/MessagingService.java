package com.detectify.screenshotservice.service;

import java.util.List;

public interface MessagingService<T> {

    void send(String topic, T payload);
    void send(String topic, List<T> payloads);

}
