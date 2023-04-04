package com.grekoff.market.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderDto() {
    }

    public OrderDto(Long id, List<OrderItemDto> items, BigDecimal totalPrice, LocalDateTime createdAt) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }
}
