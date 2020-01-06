package com.detectify.screenshotservice.repository;

import com.detectify.screenshotservice.domain.ScreenShot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ScreenShotRepository extends MongoRepository<ScreenShot, String> {

    Optional<ScreenShot> findByUrl(String url);

}
