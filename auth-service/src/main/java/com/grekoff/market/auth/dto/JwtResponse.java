package com.grekoff.market.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ на запрос токена")
public class JwtResponse {
    @Schema(description = "Строка токена")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JwtResponse() {
    }

    public JwtResponse(String token) {
        this.token = token;
    }
}
