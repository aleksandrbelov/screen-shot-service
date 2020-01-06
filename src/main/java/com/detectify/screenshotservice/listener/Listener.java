package com.detectify.screenshotservice.listener;

public interface Listener<T> {

    void listen(String topic, T data);

}
