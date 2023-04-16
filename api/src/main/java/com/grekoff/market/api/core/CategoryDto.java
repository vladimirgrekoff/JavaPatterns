package com.grekoff.market.api.core;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель категории")
public class CategoryDto {
    @Schema(description = "ID категории", requiredMode = Schema.RequiredMode.AUTO, example = "2")
    private Long id;
    @Schema(description = "Имя категории",  requiredMode = Schema.RequiredMode.REQUIRED, example = "Бытовая химия")
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CategoryDto() {
    }

    public CategoryDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
