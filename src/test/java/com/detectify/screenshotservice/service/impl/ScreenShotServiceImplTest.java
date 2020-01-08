package com.detectify.screenshotservice.service.impl;

import com.detectify.screenshotservice.domain.ScreenShot;
import com.detectify.screenshotservice.exception.ScreenShotNotFoundException;
import com.detectify.screenshotservice.repository.ScreenShotRepository;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScreenShotServiceImplTest {

    private static final String WWW_GOOGLE_COM = "http://www.google.com";

    private byte[] image;

    private ScreenShotServiceImpl service;

    private ScreenShotRepository repository = mock(ScreenShotRepository.class);

    @BeforeEach
    void setUp() throws IOException {
        InputStream inputStream = new ClassPathResource("static/google-screenshot.png").getInputStream();
        image = IOUtils.toByteArray(inputStream);
        service = new ScreenShotServiceImpl(repository);
    }

    @Test
    void createScreenShot_save() {
        ScreenShot expected = ScreenShot.builder().url(WWW_GOOGLE_COM).image(Base64.getEncoder().encodeToString(image)).build();

        when(repository.findByUrl(WWW_GOOGLE_COM)).thenReturn(Optional.empty());
        when(repository.insert(expected)).thenReturn(expected);

        ScreenShot actual = service.createScreenShot(WWW_GOOGLE_COM, image);

        assertThat(actual, is(expected));
    }

    @Test
    void createScreenShot_update() {
        ScreenShot expected = ScreenShot.builder().url(WWW_GOOGLE_COM).image(Base64.getEncoder().encodeToString(image)).build();

        when(repository.findByUrl(WWW_GOOGLE_COM)).thenReturn(Optional.of(expected));
        when(repository.save(expected)).thenReturn(expected);

        ScreenShot actual = service.createScreenShot(WWW_GOOGLE_COM, image);

        assertThat(actual, is(expected));
    }

    @Test
    void getScreenShot() {
        ScreenShot expected = ScreenShot.builder().url(WWW_GOOGLE_COM).image(Base64.getEncoder().encodeToString(image)).build();

        when(repository.findByUrl(WWW_GOOGLE_COM)).thenReturn(Optional.of(expected));
        ScreenShot actual = service.getScreenShot(WWW_GOOGLE_COM);

        assertThat(actual, is(expected));
    }

    @Test
    void getScreenShot_notFound() {
        ScreenShotNotFoundException actual = Assertions.assertThrows(ScreenShotNotFoundException.class,
                () -> service.getScreenShot(WWW_GOOGLE_COM));

        assertThat(actual.getMessage(), is("Screen shot not found"));
    }
}