package com.grekoff.market.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель регистрационных данных пользователя")
public class UserRegistrationDto {

    @Schema(description = "Имя пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "Иван")
    private String firstname;
    @Schema(description = "Фамилия пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "Иванов")
    private String lastname;
    @Schema(description = "Имя пользователя в сети", requiredMode = Schema.RequiredMode.REQUIRED,  example = "user")
    private String username;
    @Schema(description = "Адрес электронной почты пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "user@gmail.com")
    private String email;
    @Schema(description = "Пароль пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "100")
    private String password;
    @Schema(description = "Подтверждение пароля пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "100")
    private String confirmPassword;

    public UserRegistrationDto() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
