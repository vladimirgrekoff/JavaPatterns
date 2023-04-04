package com.grekoff.market.api.cart.utils;


import com.grekoff.market.api.core.ProductDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public Cart(){
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }


    public void add(ProductDto p) {
        if(items != null) {
            for (CartItem item : items) {
                if (item.getProductId().equals(p.getId())) {
                    item.incrementQuantity();
                    recalculate();
                    return;
                }
            }
        }
        CartItem cartItem = new CartItem(p.getId(), p.getTitle(), 1, p.getPrice(), p.getPrice());
        items.add(cartItem);
        recalculate();
    }
    public void delete(Long id) {
        for (CartItem item : items) {
            if (item.getProductId().equals(id)) {
                if (item.getQuantity() > 1) {
                    item.decrementQuantity();
                } else {
                    items.remove(item);;
                }
                recalculate();
                return;
            }
        }
    }

    public boolean isPresentInCart(Long id) {
        boolean result = false;
        for (CartItem item : items) {
            if(item.getProductId() == id) {
                result = true;
            }
        }
        return result;
    }
    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    public void recalculate() {
        totalPrice = BigDecimal.ZERO;
        items.forEach(i -> totalPrice = totalPrice.add(i.getPrice()));
    }
}
