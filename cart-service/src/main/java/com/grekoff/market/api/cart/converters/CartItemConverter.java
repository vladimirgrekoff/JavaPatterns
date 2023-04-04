package com.grekoff.market.api.cart.converters;

import com.grekoff.market.api.cart.utils.CartItem;
import com.grekoff.market.api.carts.CartItemDto;
import org.springframework.stereotype.Component;

@Component
public class CartItemConverter {

    public CartItemDto entityToDto(CartItem c) {
        return new CartItemDto(c.getProductId(), c.getProductTitle(), c.getQuantity(), c.getPricePerProduct(), c.getPrice());
    }

}
