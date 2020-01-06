package com.detectify.screenshotservice.controller;

import com.detectify.screenshotservice.domain.ScreenShot;
import com.detectify.screenshotservice.dto.CreateScreenShotRequest;
import com.detectify.screenshotservice.service.MessagingService;
import com.detectify.screenshotservice.service.ScreenShotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {ScreenShotController.class})
class ScreenShotControllerTest {

    private static final String WWW_GOOGLE_COM = "http://www.google.com";

    @Autowired
    private ScreenShotController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScreenShotService screenShotService;

    @MockBean
    private MessagingService<String> messagingService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createScreenShots() throws Exception {
        List<String> urls = Collections.singletonList(WWW_GOOGLE_COM);
        CreateScreenShotRequest createScreenShotRequest = new CreateScreenShotRequest(urls);

        byte[] body = objectMapper.writeValueAsBytes(createScreenShotRequest);

        mockMvc.perform(post("/api/v1/screenshot")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(messagingService).send("create-screenshot", urls);
    }

    @Test
    void getScreenShot() throws Exception {
        InputStream inputStream = new ClassPathResource("static/google-screenshot.png").getInputStream();
        byte[] image = IOUtils.toByteArray(inputStream);

        ScreenShot screenShot = ScreenShot.builder().url(WWW_GOOGLE_COM).image(image).build();
        when(screenShotService.getScreenShot(WWW_GOOGLE_COM)).thenReturn(screenShot);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/screenshot").param("url", WWW_GOOGLE_COM))
                .andReturn();

        byte[] actual = mvcResult.getResponse().getContentAsByteArray();

        assertThat(actual, is(image));
    }
}