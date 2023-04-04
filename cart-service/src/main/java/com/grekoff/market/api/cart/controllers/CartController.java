package com.grekoff.market.api.cart.controllers;


import com.grekoff.market.api.dto.StringResponse;
import com.grekoff.market.api.cart.converters.CartConverter;
import com.grekoff.market.api.cart.services.CartService;
import com.grekoff.market.api.carts.CartDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Корзина", description = "Методы работы с корзинами заказов и выбранными продуктами")
//@CrossOrigin("*")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    // http://localhost:8190/market-cart/api/v1/cart

    @Operation(
            summary = "Запрос на получение идентификатора гостевой корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    )
            }
    )
    @GetMapping("/generate_id")
    public StringResponse generateGuestCartId() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @Operation(
            summary = "Запрос на получение текущей корзины со списком продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CartDto.class))
                    )
            }
    )
    @GetMapping("/{guestCartId}")
    public CartDto getCurrentCart(@RequestHeader(required = false) String username, @PathVariable String guestCartId) {
        String currentCartId = cartService.selectCartId(username, guestCartId);
        return cartConverter.entityToDto(cartService.getActualCart(currentCartId, guestCartId));
    }

    @Operation(
            summary = "Запрос на добавление в корзину продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{guestCartId}/add/{productId}")
    public void addProductToCart(@RequestHeader(required = false) String username, @PathVariable String guestCartId, @PathVariable Long productId) {
        String currentCartId = cartService.selectCartId(username, guestCartId);
        cartService.addToCart(currentCartId, productId);
    }


    @Operation(
            summary = "Запрос на удаление из корзины продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{guestCartId}/delete/{productId}")
    public void deleteProductById(@RequestHeader(required = false) String username, @PathVariable String guestCartId, @PathVariable(name = "productId") Long productId){
        String currentCartId = cartService.selectCartId(username, guestCartId);
        cartService.deleteFromCart(currentCartId, productId);
    }


    @Operation(
            summary = "Запрос на очистку корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{guestCartId}/clear")
    public void deleteAllProducts(@RequestHeader(required = false) String username, @PathVariable String guestCartId) {
        String currentCartId = cartService.selectCartId(username, guestCartId);
        cartService.clearCart(currentCartId);
    }

}

