package com.detectify.screenshotservice.listener.impl;

import com.detectify.screenshotservice.domain.ScreenShot;
import com.detectify.screenshotservice.listener.Listener;
import com.detectify.screenshotservice.service.ScreenShotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ScreenShotListener implements Listener<String> {

    private final ScreenShotService screenShotService;

    @Override
    @KafkaListener(groupId = "screenshot-service", topics = {"create-screenshot"})
    public void listen(String topic, String url) {
        log.info("Take screen shot request for url [{}]", url);
        ScreenShot screenShot = screenShotService.createScreenShot(url);
        log.info("Screen shot taken for URL:[{}] id:[{}]", screenShot.getUrl(), screenShot.getId());
    }
}
