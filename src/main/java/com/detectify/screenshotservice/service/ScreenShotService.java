package com.detectify.screenshotservice.service;

import com.detectify.screenshotservice.domain.ScreenShot;

public interface ScreenShotService {

    ScreenShot createScreenShot(String url);
    ScreenShot getScreenShot(String url);


}
