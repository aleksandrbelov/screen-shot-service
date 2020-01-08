package com.detectify.screenshotservice.service.impl;

import com.detectify.screenshotservice.domain.ScreenShot;
import com.detectify.screenshotservice.exception.ScreenShotNotFoundException;
import com.detectify.screenshotservice.repository.ScreenShotRepository;
import com.detectify.screenshotservice.service.ScreenShotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ScreenShotServiceImpl implements ScreenShotService {

    private final ScreenShotRepository screenShotRepository;

    @Override
    public ScreenShot createScreenShot(String url, byte[] image) {
        log.info("Creating screen shot for URL [{}]", url);
        return saveOrUpdate(url, image);
    }

    private ScreenShot saveOrUpdate(String url, byte[] image) {
        Optional<ScreenShot> screenShotOpt = screenShotRepository.findByUrl(url);

        if(screenShotOpt.isPresent()) {
            return updateImage(image, screenShotOpt.get());
        }

        ScreenShot screenShot = ScreenShot.builder().url(url).image(Base64.getEncoder().encodeToString(image)).build();
        return screenShotRepository.insert(screenShot);
    }

    private ScreenShot updateImage(byte[] image, ScreenShot screenShot) {
        screenShot.setImage(Base64.getEncoder().encodeToString(image));
        return screenShotRepository.save(screenShot);
    }

    @Override
    public ScreenShot getScreenShot(String url) {
        log.info("Getting screen shot for URL [{}]", url);
        return screenShotRepository.findByUrl(url)
                .orElseThrow(ScreenShotNotFoundException::new);
    }
}
