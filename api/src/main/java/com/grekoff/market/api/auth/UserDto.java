package com.grekoff.market.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collection;

@Schema(description = "Модель пользователя")
public class UserDto {
    @Schema(description = "ID пользователя", requiredMode = Schema.RequiredMode.AUTO,  example = "1")
    public Long id;
    @Schema(description = "Имя пользователя в сети", requiredMode = Schema.RequiredMode.REQUIRED,  example = "user")
    private String username;
    @Schema(description = "Пароль пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "PROTECTED")
    private String password;
    @Schema(description = "Имя пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "Иван")
    private String firstname;
    @Schema(description = "Фамилия пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "Иванов")
    private String lastname;
    @Schema(description = "Адрес электронной почты пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "user@gmail.com")
    private String email;
    @Schema(description = "Список ролей пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
    private Collection<RoleDto> roles;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Collection<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleDto> roles) {
        this.roles = roles;
    }

    public UserDto() {
    }

    public UserDto(Long id, String username, String password, String firstname, String lastname, String email, Collection<RoleDto> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.roles = roles;
    }
}
