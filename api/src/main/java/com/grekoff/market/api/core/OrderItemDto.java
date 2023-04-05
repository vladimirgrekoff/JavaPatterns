package com.grekoff.market.api.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Jacksonized
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Продукт в заказе")
public class OrderItemDto {
    @Schema(description = "ID продукта",  requiredMode = Schema.RequiredMode.AUTO, example = "1")
    private Long productId;
    @Schema(description = "Наименование продукта",  requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 255, minLength = 3, example = "Хлеб")
    private String productTitle;
    @Schema(description = "Количество единиц в заказе",  requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private int quantity;
    @Schema(description = "Цена единицы продукта",  requiredMode = Schema.RequiredMode.REQUIRED, example = "40.00")
    private BigDecimal pricePerProduct;
    @Schema(description = "Общая стоимость продукта в корзине",  requiredMode = Schema.RequiredMode.REQUIRED, example = "80.00")
    private BigDecimal price;

//    private OrderItemDto(Builder builder) {
//        productId = builder.productId;
//        productTitle = builder.productTitle;
//        quantity = builder.quantity;
//        pricePerProduct = builder.pricePerProduct;
//        price = builder.price;
//    }
//
//    public static Builder newBuilder() {
//        return new Builder();
//    }
//
//    public static Builder newBuilder(OrderItemDto copy) {
//        Builder builder = new Builder();
//        builder.productId = copy.getProductId();
//        builder.productTitle = copy.getProductTitle();
//        builder.quantity = copy.getQuantity();
//        builder.pricePerProduct = copy.getPricePerProduct();
//        builder.price = copy.getPrice();
//        return builder;
//    }
//
//    public Long getProductId() {
//        return productId;
//    }
//
//    public String getProductTitle() {
//        return productTitle;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public BigDecimal getPricePerProduct() {
//        return pricePerProduct;
//    }
//
//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public static final class Builder {
//        private Long productId;
//        private String productTitle;
//        private int quantity;
//        private BigDecimal pricePerProduct;
//        private BigDecimal price;
//
//        private Builder() {
//        }
//
//        public Builder withProductId(Long val) {
//            productId = val;
//            return this;
//        }
//
//        public Builder withProductTitle(String val) {
//            productTitle = val;
//            return this;
//        }
//
//        public Builder withQuantity(int val) {
//            quantity = val;
//            return this;
//        }
//
//        public Builder withPricePerProduct(BigDecimal val) {
//            pricePerProduct = val;
//            return this;
//        }
//
//        public Builder withPrice(BigDecimal val) {
//            price = val;
//            return this;
//        }
//
//        public OrderItemDto build() {
//            return new OrderItemDto(this);
//        }
//    }




//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }
//
//    public void setProductTitle(String productTitle) {
//        this.productTitle = productTitle;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public void setPricePerProduct(BigDecimal pricePerProduct) {
//        this.pricePerProduct = pricePerProduct;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
//
//    public OrderItemDto() {
//    }
//
//    public OrderItemDto(Long productId, String productTitle, int quantity, BigDecimal pricePerProduct, BigDecimal price) {
//        this.productId = productId;
//        this.productTitle = productTitle;
//        this.quantity = quantity;
//        this.pricePerProduct = pricePerProduct;
//        this.price = price;
//    }
}
