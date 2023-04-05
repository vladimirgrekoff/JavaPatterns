package com.grekoff.market.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Модель роли пользователя")
public class RoleDto {
    @Schema(description = "ID роли",   example = "1")
    private Long id;
    @Schema(description = "Имя роли",  example = "ROLE_USER")
    private String name;



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleDto() {
    }

    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
