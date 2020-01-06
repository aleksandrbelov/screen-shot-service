package com.detectify.screenshotservice.service;

import com.detectify.screenshotservice.domain.ScreenShot;

public interface ScreenShotService {

    ScreenShot createScreenShot(String url, byte[] image);
    ScreenShot getScreenShot(String url);


}
