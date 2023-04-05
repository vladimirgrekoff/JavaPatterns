package com.grekoff.market.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "Модель продукта")
public class ProductDto {
    @Schema(description = "ID продукта", requiredMode = Schema.RequiredMode.AUTO, example = "13")
    private Long id;
    @Schema(description = "Наименование продукта",  requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 255, minLength = 3, example = "Конфеты")
    private String title;
    @Schema(description = "Цена продукта",  requiredMode = Schema.RequiredMode.REQUIRED, example = "100.00")
    private BigDecimal price;
    @Schema(description = "Категория продукта",  requiredMode = Schema.RequiredMode.REQUIRED, example = "Продукты")
    private String categoryTitle;



    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public ProductDto() {
    }

    public ProductDto(Long id, String title, BigDecimal price, String categoryTitle) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.categoryTitle = categoryTitle;
    }
}
