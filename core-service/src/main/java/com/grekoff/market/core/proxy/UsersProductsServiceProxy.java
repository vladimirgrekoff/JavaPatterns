package com.grekoff.market.core.proxy;

import com.grekoff.market.api.core.ProductDto;
import com.grekoff.market.core.converters.ProductConverter;
import com.grekoff.market.core.entities.Product;
import com.grekoff.market.core.repositories.ProductsRepository;
import com.grekoff.market.core.services.CategoryService;
import com.grekoff.market.core.services.ProductsService;
import com.grekoff.market.core.validators.ProductValidator;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
//@AllArgsConstructor
//@RequiredArgsConstructor
public class UsersProductsServiceProxy implements UsersProductsService{
//    private static UsersProductsService usersProductsService;
    private ProductsService productsService;
    private ProductsRepository productsRepository;
    private Map<String, Object> cache;
    @Data
    @RequiredArgsConstructor
    private  class Object {
        UsersProductsService cashedUsersProductsService;
        ProductsRepository cashedProductsRepository;
        List<ProductDto> productDtoList;
        Page<Product> productPage;
        Optional<Product> productOptional;
    }

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
//        System.out.println("Ссылка productsService " + productsService );
    }

    public void setProductsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
//        System.out.println("Ссылка productsRepository " + productsRepository );
    }

    @PostConstruct
    public void init(){
        setProductsService(productsService);
        setProductsRepository(productsRepository);
        this.cache = new HashMap<>();
    }



    @Override
    public List<ProductDto> findAll() {
        if (!cache.containsKey("findAll")) {
            Object cashedProductsService = new Object();
            cashedProductsService.setProductDtoList(productsService.findAll());
            cache.put("findAll", cashedProductsService);
//        System.out.println("кеширование: productsService.findAll()");
        }

        return cache.get("findAll").getProductDtoList();
//        return null;
    }


    @Override
    public Page<Product> findAllPages(Integer minPrice, Integer maxPrice, String partTitle, Integer offset, Integer size, Boolean first, Boolean last, Integer currentPage) {
        String currentPageValue;
        currentPageValue = (minPrice + ", " + maxPrice + ", " + partTitle + ", " + offset + ", " + size + ", " + first + ", " + last + ", " + currentPage).toString();

        if (!cache.containsKey(currentPageValue)) {
            Object cashedPageProducts = new Object();
            cashedPageProducts.setProductPage(productsService.findAllPages(minPrice, maxPrice, partTitle, offset, size, first, last, currentPage));
            cache.put(currentPageValue, cashedPageProducts);
//            System.out.println("кеширование cashedPageProducts");
        }
        return cache.get(currentPageValue).getProductPage();
    }
    @Override
    public Optional<Product> findById(Long id) {

        if (!cache.containsKey("productsRepository")) {
            Object cashedProductsRepository = new Object();
            cashedProductsRepository.setCashedProductsRepository(productsRepository);
            cache.put("productsRepository", cashedProductsRepository);
//            System.out.println("кеширование productsRepository");
        }
        return (cache.get("productsRepository").getCashedProductsRepository()).findById(id);
    }
}
