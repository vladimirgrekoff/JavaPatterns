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
@AllArgsConstructor
//@RequiredArgsConstructor
public class UsersProductsServiceProxy implements UsersProductsService{
    private static UsersProductsService usersProductsService;
    private ProductsRepository productsRepository;
    private Map<String, Object> cache;
    private ProductsService productsService;
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
        System.out.println("Ссылка productsService " + productsService );
    }

    public void setProductsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
        System.out.println("Ссылка productsRepository " + productsRepository );
    }

    @PostConstruct
    public void init(){
        setProductsService(productsService);
        setProductsRepository(productsRepository);
        this.cache = new HashMap<>();
    }



    private void checkService() {
        if (usersProductsService == null){
            usersProductsService = productsService;
        }
    }

    @Override
    public List<ProductDto> findAll() {
        if (!cache.containsKey("findAll")) {
            Object cashedProductsService = new Object();
            cashedProductsService.setProductDtoList(usersProductsService.findAll());
            cache.put("findAll", cashedProductsService);
        }
        System.out.println("cashedObject findAll: " + cache.get("findAll").getProductDtoList());

        return cache.get("findAll").getProductDtoList();
//        return null;
    }


    @Override
    public Page<Product> findAllPages(Integer minPrice, Integer maxPrice, String partTitle, Integer offset, Integer size, Boolean first, Boolean last, Integer currentPage) {
        checkService();
        System.out.println("Страницы " + "minPrice " + minPrice + ", " + "maxPrice " + maxPrice + ", " + "partTitle " + partTitle + ", " + "offset " + offset + ", " + "size " + size + ", " + "first " + first + ", " + "last " + last + ", " + "currentPage " + currentPage);

        return usersProductsService.findAllPages(minPrice, maxPrice, partTitle, offset, size, first, last, currentPage);
    }
    @Override
    public Optional<Product> findById(Long id) {
        String value = id.toString();

        if (!cache.containsKey("productsRepository")) {
            Object cashedProductsRepository = new Object();
            cashedProductsRepository.setCashedProductsRepository(productsRepository);
            cache.put("productsRepository", cashedProductsRepository);
//            System.out.println("кеширование productsRepository");
        }
//        System.out.println("кенированный productsRepository: " + cache.get("productsRepository").getCashedProductsRepository());
//        System.out.println("количество продуктов в productsRepository: " + cache.get("productsRepository").getCashedProductsRepository().countProducts());

        return (cache.get("productsRepository").getCashedProductsRepository()).findById(id);
    }
}
