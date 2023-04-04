package com.grekoff.market.core.services;

import com.grekoff.market.api.carts.CartDto;
import com.grekoff.market.api.auth.UserDto;
import com.grekoff.market.core.entities.Order;
import com.grekoff.market.core.entities.OrderItem;
import com.grekoff.market.core.integrations.CartServiceIntegration;
import com.grekoff.market.core.integrations.AuthServiceIntegration;
import com.grekoff.market.core.repositories.OrdersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final ProductsService productsService;
    private final OrdersRepository ordersRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final AuthServiceIntegration authServiceIntegration;

    public List<Order> findUserOrders(String username) {
        return ordersRepository.findAllByUsername(username);
    }
    @Transactional
    public void createOrder(String username) {
        UserDto userDto = authServiceIntegration.getCurrentUserInfo(username);
        CartDto cartDto = cartServiceIntegration.getCurrentCart(username);
        Order order = new Order();

        order.setUsername(username);
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setOrderItems(cartDto.getItems().stream().map(
                cartItem -> new OrderItem(
                        productsService.findById(cartItem.getProductId()).get(),
                        order,
                        cartItem.getQuantity(),
                        cartItem.getPricePerProduct(),
                        cartItem.getPrice()
                )
        ).collect(Collectors.toList()));
        ordersRepository.save(order);
        cartServiceIntegration.clear(username);
    }
}