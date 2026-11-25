package com.jackie.matchingtradingsystemserver.Response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class CaptchaResponse {

    private String id;

    private String imageBase64;


    public CaptchaResponse(String id, String imageBase64) {
        this.id = id;
        this.imageBase64 = imageBase64;

    }
}
