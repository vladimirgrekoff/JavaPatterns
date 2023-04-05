package com.grekoff.market.api.carts;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Jacksonized
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель корзины заказов")
public class CartDto {
    @Schema(description = "Список продуктов в корзине")
    private List<CartItemDto> items;
    @Schema(description = "Общая стоимость корзины", example = "1000")
    private BigDecimal totalPrice;

    public List<CartItemDto> getItems() {
        return items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

//    public void setItems(List<CartItemDto> items) {
//        this.items = items;
//    }
//
//    public void setTotalPrice(BigDecimal totalPrice) {
//        this.totalPrice = totalPrice;
//    }

//    public CartDto() {
//    }
//
//    public CartDto(List<CartItemDto> items, BigDecimal totalPrice) {
//        this.items = items;
//        this.totalPrice = totalPrice;
//    }
}
