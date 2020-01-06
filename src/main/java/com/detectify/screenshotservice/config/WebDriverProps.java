package com.detectify.screenshotservice.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("webdriver")
@Data
public class WebDriverProps {
    private String location;
    private List<String> options;
}
