package com.detectify.screenshotservice.repository;

import com.detectify.screenshotservice.domain.ScreenShot;
import org.apache.commons.io.IOUtils;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import static org.hamcrest.CoreMatchers.is;

@DataMongoTest
@RunWith(SpringRunner.class)
class ScreenShotRepositoryTest {

    private static final String WWW_GOOGLE_COM = "http://www.google.com";

    @Autowired
    private ScreenShotRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void findByUrl() throws IOException {
        InputStream inputStream = new ClassPathResource("static/google-screenshot.png").getInputStream();
        byte[] image = IOUtils.toByteArray(inputStream);

        ScreenShot screenShot = ScreenShot.builder().image(Base64.getEncoder().encodeToString(image)).url(WWW_GOOGLE_COM).build();

        ScreenShot expected = mongoTemplate.save(screenShot);

        ScreenShot actual = repository.findByUrl(WWW_GOOGLE_COM).orElse(null);

        MatcherAssert.assertThat(actual, is(expected));
    }
}