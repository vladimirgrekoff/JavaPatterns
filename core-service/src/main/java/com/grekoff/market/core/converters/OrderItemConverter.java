package com.grekoff.market.core.converters;

import org.springframework.stereotype.Component;
import com.grekoff.market.api.core.OrderItemDto;
import com.grekoff.market.core.entities.OrderItem;

@Component
public class OrderItemConverter {
    public OrderItemDto entityToDto(OrderItem o) {
        return new OrderItemDto(o.getProduct().getId(), o.getProduct().getTitle(), o.getQuantity(), o.getPricePerProduct(), o.getPrice());
    }
}
