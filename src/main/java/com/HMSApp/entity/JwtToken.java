package com.HMSApp.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtToken {
    private String token;
    private String type;
}
