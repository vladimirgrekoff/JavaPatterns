package com.grekoff.market.core.proxy;

import com.grekoff.market.api.core.PageDto;
import com.grekoff.market.api.core.ProductDto;
import com.grekoff.market.core.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface UsersProductsService {
//    List<ProductDto> findAll();
    List<Product> findAll();

    Page<Product> findAllPages(Integer minPrice, Integer maxPrice, String partTitle, Integer offset, Integer size, Boolean first, Boolean last, Integer currentPage);
//    Page<ProductDto> findAllPages(Integer minPrice, Integer maxPrice, String partTitle, Integer offset, Integer size, Boolean first, Boolean last, Integer currentPage);
//    PageDto<ProductDto> findAllPages(Integer minPrice, Integer maxPrice, String partTitle, Integer offset, Integer size, Boolean first, Boolean last, Integer currentPage);

    Optional<Product> findById(Long id);
//    ProductDto findById(Long id);
}
