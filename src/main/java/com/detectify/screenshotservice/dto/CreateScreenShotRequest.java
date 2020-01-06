package com.detectify.screenshotservice.dto;

import com.detectify.screenshotservice.constraint.URLS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateScreenShotRequest {

    @URLS
    private List<String> urls;

}
