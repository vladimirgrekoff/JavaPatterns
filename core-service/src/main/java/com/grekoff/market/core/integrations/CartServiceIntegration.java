package com.grekoff.market.core.integrations;

import com.grekoff.market.api.carts.CartDto;
import com.grekoff.market.api.exceptions.CartServiceAppError;
import com.grekoff.market.core.exceptions.CartServiceIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public void clear(String username) {
        cartServiceWebClient.delete()
                .uri("/api/v1/cart/currentCartId/clear")
                .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public CartDto getCurrentCart(String username) {
//                .uri("http://localhost:8190/market-cart/api/v1/cart")
        return cartServiceWebClient.get()
                .uri("/api/v1/cart/currentCartId")
                .header("username", username)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError(), // HttpStatus::is4xxClientError
                        clientResponse -> clientResponse.bodyToMono(CartServiceAppError.class).map(
                                body -> {
                                    if (body.getStatusCode() == (CartServiceAppError.CartServiceErrors.CART_NOT_FOUND.getCode())) {
                                        return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: корзина не найдена");
                                    }
                                    if (body.getStatusCode() == (CartServiceAppError.CartServiceErrors.CART_IS_BROKEN.getCode())) {
                                        return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: корзина сломана");
                                    }
                                    return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: причина неизвестна");
                                }
                        )
                )
//                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин")))
//                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new CartServiceIntegrationException("Сервис корзин сломался")))
                .bodyToMono(CartDto.class)
                .block();
    }


}
