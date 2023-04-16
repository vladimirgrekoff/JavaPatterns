package com.grekoff.market.core.controllers;

import com.grekoff.market.api.core.PageDto;
import com.grekoff.market.api.core.ProductDto;
import com.grekoff.market.core.converters.ProductConverter;
import com.grekoff.market.core.entities.Product;
import com.grekoff.market.core.exceptions.AppError;
import com.grekoff.market.core.exceptions.ResourceNotFoundException;
import com.grekoff.market.core.mappers.ProductMapper;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


//import org.hibernate.mapping.List;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductsController {
    private final ProductsService productsService;
    private final ProductConverter productConverter;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;

    // http://localhost:8189/market-core/api/v1/admin/products

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
//        List<ProductDto> productDtoList = new ArrayList<>();
//        List<Product> productList = productsService.findAll();
//        for (Product p: productList) {
//            ProductDto productDto = productConverter.entityToDto(p);
//            productDtoList.add(productDto);
//        }
//        return productDtoList;

        return productsService.findAll().stream()
                .map(productMapper::mapToDto)
                .toList();

//        return productsService.findAll();

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
        Page<ProductDto> pageProductDto = productsService.findAllPages(minPrice, maxPrice, partTitle, offset, limit, first, last, currentPage).map(productConverter::entityToDto);
//        Page<ProductDto> pageProductDto = productsService.findAllPages(minPrice, maxPrice, partTitle, offset, limit, first, last, currentPage);
        PageDto<ProductDto> response = new PageDto<>();
        response.setPage(pageProductDto.getNumber());
        response.setItems(pageProductDto.getContent());
        response.setTotalPage(pageProductDto.getTotalPages());
//        PageDto<ProductDto> response = productsService.findAllPages(minPrice, maxPrice, partTitle, offset, limit, first, last, currentPage);
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
        return productConverter.entityToDto(productsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + id + " не найден")));
//        return productsService.findById(id);
    }

    @Operation(
            summary = "Запрос на создание нового продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно создан", responseCode = "201"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewProducts(@RequestBody ProductDto productDto) {
        productsService.createNewProduct(productDto);
    }

    @Operation(
            summary = "Запрос на изменение параметров продукта",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        productsService.update(productDto);
    }

    @Operation(
            summary = "Запрос на удаление продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id) {
        productsService.deleteById(id);
    }
}
