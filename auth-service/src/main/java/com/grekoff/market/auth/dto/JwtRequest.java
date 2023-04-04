package com.grekoff.market.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос токена")
public class JwtRequest {
    @Schema(description = "Имя пользователя в сети", requiredMode = Schema.RequiredMode.REQUIRED,  example = "user")
    private String username;
    @Schema(description = "Пароль пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "100")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
