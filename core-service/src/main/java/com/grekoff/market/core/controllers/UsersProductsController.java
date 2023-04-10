package com.grekoff.market.core.controllers;

import com.grekoff.market.api.core.PageDto;
import com.grekoff.market.api.core.ProductDto;
import com.grekoff.market.core.converters.ProductConverter;
import com.grekoff.market.core.entities.Product;
import com.grekoff.market.core.exceptions.AppError;
import com.grekoff.market.core.exceptions.ResourceNotFoundException;
import com.grekoff.market.core.proxy.UsersProductsService;
import com.grekoff.market.core.proxy.UsersProductsServiceProxy;
import com.grekoff.market.core.repositories.ProductsRepository;
import com.grekoff.market.core.services.CategoryService;
import com.grekoff.market.core.services.ProductsService;
import com.grekoff.market.core.validators.ProductValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class UsersProductsController {
    private final ProductConverter productConverter;
    private final UsersProductsServiceProxy usersProductsServiceProxy;

    // http://localhost:8189/market-core/api/v1/products

    @Operation(
            summary = "Запрос на получение полного списка продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))}
                    )
            }
    )
    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
        return usersProductsServiceProxy.findAll();
    }

    @Operation(
            summary = "Запрос на получение отфильтрованного списка продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )

            }
    )
    @GetMapping("/pages")
//    public Page<ProductDto> getAllPagesProducts(
    public PageDto<ProductDto> getAllPagesProducts(
            @Parameter(description = "Минимальная цена продукта")
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @Parameter(description = "Максимальная цена продукта")
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @Parameter(description = "Идентификатор продукта")
            @RequestParam(name = "part_title", required = false) String partTitle,
            @Parameter(description = "Номер страницы или смещение от текущей")
            @RequestParam(name = "offset", defaultValue = "0") Integer offset,
            @Parameter(description = "Количество элементов на странице")
            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
            @Parameter(description = "Показать первую страницу")
            @RequestParam(name = "first", defaultValue = "false") Boolean first,
            @Parameter(description = "Показать последнюю страницу")
            @RequestParam(name = "last", defaultValue = "false") Boolean last,
            @Parameter(description = "Текущая страница")
            @RequestParam(name = "current_page", defaultValue = "0") Integer currentPage

    ) {
        Page<ProductDto> pageProductDto = usersProductsServiceProxy.findAllPages(minPrice, maxPrice, partTitle, offset, limit, first, last, currentPage).map(productConverter::entityToDto);
        PageDto<ProductDto> response = new PageDto<>();
        response.setPage(pageProductDto.getNumber());
        response.setItems(pageProductDto.getContent());
        response.setTotalPage(pageProductDto.getTotalPages());

        return response;
    }

    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id) {
        return productConverter.entityToDto(usersProductsServiceProxy.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + id + " не найден")));
    }



}
