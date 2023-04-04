package com.grekoff.market.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.grekoff.market.core.converters.OrderConverter;
import com.grekoff.market.core.services.OrdersService;
import com.grekoff.market.api.core.OrderDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Методы работы с заказами продуктов")
public class OrderController {

    private final OrdersService ordersService;
    private final OrderConverter orderConverter;

    // http://localhost:8189/market-core/api/v1/orders

    @Operation(
            summary = "Запрос на получение списка заказов пользователя по username",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))}
                    )
            }
    )
    @GetMapping
    public List<OrderDto> getUserOrders(@RequestHeader @Parameter(description = "username", required = true) String username) {
        return ordersService.findUserOrders(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @Operation(
            summary = "Запрос на создание нового заказа по username",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "201"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader @Parameter(description = "username", required = true) String username) {
        ordersService.createOrder(username);
    }
}
