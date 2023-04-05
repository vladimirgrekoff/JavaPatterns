package com.grekoff.market.core.converters;

import org.springframework.stereotype.Component;
import com.grekoff.market.api.core.OrderItemDto;
import com.grekoff.market.core.entities.OrderItem;

@Component
public class OrderItemConverter {
    public OrderItemDto entityToDto(OrderItem o) {
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .productId(o.getProduct().getId())
                .productTitle(o.getProduct().getTitle())
                .quantity(o.getQuantity())
                .pricePerProduct(o.getPricePerProduct())
                .price(o.getPrice())
                .build();
        return orderItemDto;
    }
}
