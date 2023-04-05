package com.grekoff.market.api.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Jacksonized
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель заказа")
public class OrderDto {
    @Schema(description = "ID заказа",  requiredMode = Schema.RequiredMode.AUTO, example = "1")
    private Long id;
    @Schema(description = "Список продуктов в заказе")
    private List<OrderItemDto> items;
    @Schema(description = "Общая стоимость заказа", example = "1000")
    private BigDecimal totalPrice;

    @Schema(description = "Дата заказа")
    private LocalDateTime createdAt;

//    private OrderDto(Builder builder) {
//        id = builder.id;
//        items = builder.items;
//        totalPrice = builder.totalPrice;
//        createdAt = builder.createdAt;
//    }
//
//    public static Builder newBuilder() {
//        return new Builder();
//    }
//
//    public static Builder newBuilder(OrderDto copy) {
//        Builder builder = new Builder();
//        builder.id = copy.getId();
//        builder.items = copy.getItems();
//        builder.totalPrice = copy.getTotalPrice();
//        builder.createdAt = copy.getCreatedAt();
//        return builder;
//    }
//
//    private Long getId() {
//        return id;
//    }
//
//
//    public List<OrderItemDto> getItems() {
//        return items;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//
//    public BigDecimal getTotalPrice() {
//        return totalPrice;
//    }
//
//    public static final class Builder {
//        private Long id;
//        private List<OrderItemDto> items;
//        private BigDecimal totalPrice;
//        private LocalDateTime createdAt;
//
//        private Builder() {
//        }
//
//        public Builder withId(Long val) {
//            id = val;
//            return this;
//        }
//
//        public Builder withItems(List<OrderItemDto> val) {
//            items = val;
//            return this;
//        }
//
//        public Builder withTotalPrice(BigDecimal val) {
//            totalPrice = val;
//            return this;
//        }
//
//        public Builder withCreatedAt(LocalDateTime val) {
//            createdAt = val;
//            return this;
//        }
//
//        public OrderDto build() {
//            return new OrderDto(this);
//        }
//    }




//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setItems(List<OrderItemDto> items) {
//        this.items = items;
//    }
//
//    public void setTotalPrice(BigDecimal totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//
//    public OrderDto() {
//    }
//
//    public OrderDto(Long id, List<OrderItemDto> items, BigDecimal totalPrice, LocalDateTime createdAt) {
//        this.id = id;
//        this.items = items;
//        this.totalPrice = totalPrice;
//        this.createdAt = createdAt;
//    }
}
