package com.grekoff.market.api.carts;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Продукт в корзине заказов")
public class CartItemDto {
    @Schema(description = "ID продукта",  requiredMode = Schema.RequiredMode.AUTO, example = "1")
    private Long productId;
    @Schema(description = "Наименование продукта",  requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 255, minLength = 3, example = "Хлеб")
    private String productTitle;
    @Schema(description = "Количество единиц в корзине",  requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private int quantity;
    @Schema(description = "Цена единицы продукта",  requiredMode = Schema.RequiredMode.REQUIRED, example = "40.00")
    private BigDecimal pricePerProduct;
    @Schema(description = "Общая стоимость продукта в корзине",  requiredMode = Schema.RequiredMode.REQUIRED, example = "80.00")
    private BigDecimal price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CartItemDto() {
    }

    public CartItemDto(Long productId, String productTitle, int quantity, BigDecimal pricePerProduct, BigDecimal price) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }
}
