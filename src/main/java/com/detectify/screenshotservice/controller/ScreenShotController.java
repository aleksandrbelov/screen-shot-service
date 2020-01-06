package com.detectify.screenshotservice.controller;

import com.detectify.screenshotservice.domain.ScreenShot;
import com.detectify.screenshotservice.dto.CreateScreenShotRequest;
import com.detectify.screenshotservice.service.MessagingService;
import com.detectify.screenshotservice.service.ScreenShotService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/screenshot")
@AllArgsConstructor
public class ScreenShotController {

    private final ScreenShotService screenShotService;
    private final MessagingService<String> messagingService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void createScreenShots(@RequestBody @Valid CreateScreenShotRequest request) {
        messagingService.send("create-screenshot", request.getUrls());
    }

    @GetMapping(produces = {MediaType.IMAGE_PNG_VALUE})
    public Resource getScreenShot(@RequestParam("url") String url) {
        ScreenShot screenShot = screenShotService.getScreenShot(url);
        return new ByteArrayResource(screenShot.getImage());
    }


}
