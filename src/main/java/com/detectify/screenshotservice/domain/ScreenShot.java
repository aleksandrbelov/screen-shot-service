package com.detectify.screenshotservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "screenshot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenShot {

    @Id
    private String id;
    @Indexed(unique = true)
    private String url;
    private byte[] image;

}
