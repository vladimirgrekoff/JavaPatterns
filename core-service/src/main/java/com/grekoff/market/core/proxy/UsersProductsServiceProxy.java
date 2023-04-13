package com.grekoff.market.core.proxy;


import com.grekoff.market.api.core.ProductDto;
import com.grekoff.market.core.entities.Product;
import com.grekoff.market.core.event.ChangedDBProductsEvent;
import com.grekoff.market.core.listener.Listener;
import com.grekoff.market.core.repositories.ProductsRepository;
import com.grekoff.market.core.services.CategoryService;
import com.grekoff.market.core.services.ProductsService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
//@NoArgsConstructor

public class UsersProductsServiceProxy implements UsersProductsService, Listener {
//   private static UsersProductsService usersProductsService;

    private final ProductsService productsService;
    private final CategoryService categoryService;
    private final ProductsRepository productsRepository;
    private RedisTemplate<String, Object> redisTemplate;
    private Map<String, Object> cache;

//    @Autowired
    private final JedisConnectionFactory jedisConnectionFactory;

//    public CategoryService getCategoryService() {
//        return categoryService;
//    }

    @Data
    private static class Object {
        ProductsRepository cashedProductsRepository;
        List<ProductDto> productDtoList;
        Page<Product> productPage;
    }

//    @Autowired
//    public void setProductsService(ProductsService productsService) {
//        this.productsService = productsService;
////        System.out.println("Ссылка productsService " + productsService );
//    }
//    @Autowired
//    public void setProductsRepository(ProductsRepository productsRepository) {
//        this.productsRepository = productsRepository;
////        System.out.println("Ссылка productsRepository " + productsRepository );
//    }


    @PostConstruct
    public void init(){
        this.cache = new HashMap<>();
        this.redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.afterPropertiesSet();

//        System.out.println("Ссылка categoryService " + categoryService );
//        System.out.println("Ссылка productsService " + productsService );
//        System.out.println("Ссылка productsRepository " + productsRepository );
//        System.out.println("ConnectionFactory : " + redisTemplate.getConnectionFactory());
//        System.out.println("KeySerializer : " + redisTemplate.getKeySerializer());
//        System.out.println("ValueSerializer : " + redisTemplate.getValueSerializer());
    }


    @Override
    public void onEventReceived(ChangedDBProductsEvent event) {
        System.out.println(event.getMessage());///////////////////////////////////////////////
        cache.clear();
        if (redisTemplate.hasKey("findAll")) {
            Object cashedProductsService = new Object();
            cashedProductsService.setProductDtoList(productsService.findAll());
//            cache.put("findAll", cashedProductsService);
            redisTemplate.opsForValue().set("findAll", cashedProductsService);
//        System.out.println("кеширование: productsService.findAll()");
        }
    }


    @Override
    public List<ProductDto> findAll() {
        if (!cache.containsKey("findAll")) {
//        if (!redisTemplate.hasKey("findAll")) {
            Object cashedProductsService = new Object();
            cashedProductsService.setProductDtoList(productsService.findAll());
            cache.put("findAll", cashedProductsService);
//            redisTemplate.opsForValue().set("findAll", cashedProductsService);
//        System.out.println("кеширование: productsService.findAll()");
        }
        return cache.get("findAll").getProductDtoList();
//        return Objects.requireNonNull(redisTemplate.opsForValue().get("findAll")).getProductDtoList();
    }


    @Override
    public Page<Product> findAllPages(Integer minPrice, Integer maxPrice, String partTitle, Integer offset, Integer size, Boolean first, Boolean last, Integer currentPage) {
        String currentPageValue;
//        currentPageValue = (minPrice + ", " + maxPrice + ", " + partTitle + ", " + offset + ", " + size + ", " + first + ", " + last + ", " + currentPage).toString();
        currentPageValue = (minPrice + "_" + maxPrice + "_" + partTitle + "_" + first + "_" + last + "_"  + currentPage).toString();

        if (!cache.containsKey(currentPageValue)) {
//        if (!redisTemplate.hasKey(currentPageValue)) {
            Object cashedPageProducts = new Object();
            cashedPageProducts.setProductPage(productsService.findAllPages(minPrice, maxPrice, partTitle, offset, size, first, last, currentPage));
            cache.put(currentPageValue, cashedPageProducts);
//            redisTemplate.opsForValue().set(currentPageValue, cashedPageProducts);
//            System.out.println("кеширование cashedPageProducts");
        }
        return cache.get(currentPageValue).getProductPage();
//        return (Objects.requireNonNull(redisTemplate.opsForValue().get(currentPageValue))).getProductPage();
    }
    @Override
    public Optional<Product> findById(Long id) {

        if (!cache.containsKey("productsRepository")) {
//        if (!redisTemplate.hasKey("productsRepository")) {
            Object cashedProductsRepository = new Object();
            cashedProductsRepository.setCashedProductsRepository(productsRepository);
            cache.put("productsRepository", cashedProductsRepository);
//            redisTemplate.opsForValue().set("productsRepository", cashedProductsRepository);
//            System.out.println("кеширование productsRepository");
        }

        return cache.get("productsRepository").getCashedProductsRepository().findById(id);
//        return (redisTemplate.opsForValue().get("productsRepository").getCashedProductsRepository()).findById(id);
    }
}
