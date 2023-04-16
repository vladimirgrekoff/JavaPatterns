package com.grekoff.market.core.proxy;


//import com.grekoff.market.core.entities.Page;
import com.grekoff.market.core.entities.Product;
import com.grekoff.market.core.event.ChangedDBProductsEvent;
import com.grekoff.market.core.listener.Listener;
import com.grekoff.market.core.repositories.ProductsRepository;
import com.grekoff.market.core.services.CategoryService;
import com.grekoff.market.core.services.ProductsService;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
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
//    private RedisTemplate<String, Object> redisTemplate;
    private Map<String, Object> cacheData;

//    @Autowired
//    private final JedisConnectionFactory jedisConnectionFactory;

//    public CategoryService getCategoryService() {
//        return categoryService;
//    }

//    @Data
//    private static class Object {
////        List<ProductDto> productDtoList;
//        Optional<Product> productOptional;
////        ProductDto productDto;
//        List<Product> productList;
//        Page<Product> productPage;
////        Page<ProductDto> productDtoPage;
////        PageDto<ProductDto> productDtoPageDto;
//    }
    @Data
    private class Object {
        Optional<Product> productOptional;
        List<Product> productList;
        Page<Product> productPage;
}

//    @RequiredArgsConstructor
    @Cacheable
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class Cache extends Object {
        public Map<String, Object> cacheData;

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
        this.cacheData = new HashMap<>();
//        this.redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
////        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setConnectionFactory(jedisConnectionFactory);
//        redisTemplate.afterPropertiesSet();

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
        cacheData.clear();
    }


//    public Cache getCache(String cacheId) {
//        if(!redisTemplate.hasKey(cacheId)){
//            Cache cache = new Cache();
//            redisTemplate.opsForValue().set(cacheId,  cache);
//        }
//        return (Cache) redisTemplate.opsForValue().get(cacheId);
//    }

//    @Override
//    public List<ProductDto> findAll() {
//        if (!cache.containsKey("findAll")) {
////        if (!redisTemplate.hasKey("findAll")) {
//            Object cashedProductsDto = new Object();
//            cashedProductsDto.setProductDtoList(productsService.findAll());
//            cache.put("findAll", cashedProductsDto);
////            redisTemplate.opsForValue().set("findAll", cashedProductsService);
////        System.out.println("кеширование: productsService.findAll()");
//        }
//        return cache.get("findAll").getProductDtoList();
////        return Objects.requireNonNull(redisTemplate.opsForValue().get("findAll")).getProductDtoList();
//    }

    @Override
    public List<Product> findAll() {
//        Cache cache = getCache("cache");
//        if (cache != null) {
//            System.out.println("cache");
//        }

            if (!cacheData.containsKey("findAll")) {
//            if (!cache.cacheData.containsKey("findAll")) {
//        if (!redisTemplate.hasKey("findAll")) {
                Object cashedListProducts = new Object();
                cashedListProducts.setProductList(productsService.findAll());
                cacheData.put("findAll", cashedListProducts);
//                cache.cacheData.put("findAll", cashedListProducts);
//            redisTemplate.opsForValue().set("cache", (Object) cache);
//        System.out.println("кеширование: productsService.findAll()");
            }
            return cacheData.get("findAll").getProductList();
//            return cache.cacheData.get("findAll").getProductList();
//        return Objects.requireNonNull(redisTemplate.opsForValue().get("findAll")).getProductList();

    }


    @Override
    public Page<Product> findAllPages(Integer minPrice, Integer maxPrice, String partTitle, Integer offset, Integer size, Boolean first, Boolean last, Integer currentPage) {
//    public PageDto<ProductDto> findAllPages(Integer minPrice, Integer maxPrice, String partTitle, Integer offset, Integer size, Boolean first, Boolean last, Integer currentPage) {
        String currentPageValue;
        currentPageValue = (minPrice + "_" + maxPrice + "_" + partTitle + "_" + offset + "_" + size + "_" + first + "_" + last + "_" + currentPage).toString();
//        currentPageValue = (minPrice + "_" + maxPrice + "_" + partTitle + "_" + first + "_" + last + "_"  + currentPage).toString();

        if (!cacheData.containsKey(currentPageValue)) {
//        if (!redisTemplate.hasKey(currentPageValue)) {
            Object cashedPageProducts = new Object();
            cashedPageProducts.setProductPage(productsService.findAllPages(minPrice, maxPrice, partTitle, offset, size, first, last, currentPage));
//            Page<ProductDto> pageProductDto = productsService.findAllPages(minPrice, maxPrice, partTitle, offset, size, first, last, currentPage);
//            PageDto<ProductDto> pageDtoProductDto = productsService.findAllPages(minPrice, maxPrice, partTitle, offset, size, first, last, currentPage);
//            PageDto<ProductDto> pageDtoProductDto = new PageDto<>();
//            pageDtoProductDto.setPage(pageProductDto.getNumber());
//            pageDtoProductDto.setItems(pageProductDto.getContent());
//            pageDtoProductDto.setTotalPage(pageProductDto.getTotalPages());

//            cashedPageDtoProductsDto.setProductDtoPageDto(pageDtoProductDto);
            cacheData.put(currentPageValue, cashedPageProducts);
//            redisTemplate.opsForValue().set(currentPageValue, cashedPageDtoProductsDto);
//            System.out.println("кеширование cashedPageProducts");
        }
        return cacheData.get(currentPageValue).getProductPage();
//        return (Objects.requireNonNull(redisTemplate.opsForValue().get(currentPageValue))).getProductDtoPageDto();
    }

//    @Override
//    public Optional<Product> findById(Long id) {
//
//        if (!cache.containsKey("productsRepository")) {
////        if (!redisTemplate.hasKey("productsRepository")) {
//            Object cashedProductsRepository = new Object();
//            cashedProductsRepository.setCashedProductsRepository(productsRepository);
//            cache.put("productsRepository", cashedProductsRepository);
////            redisTemplate.opsForValue().set("productsRepository", cashedProductsRepository);
////            System.out.println("кеширование productsRepository");
//        }
//
//        return cache.get("productsRepository").getCashedProductsRepository().findById(id);
////        return (redisTemplate.opsForValue().get("productsRepository").getCashedProductsRepository()).findById(id);
//    }

    @Override
    public Optional<Product> findById(Long id) {
        String productId = id.toString();
        if (!cacheData.containsKey(productId)) {
//        if (!redisTemplate.hasKey(productId)) {
            Object cashedProduct = new Object();
            cashedProduct.setProductOptional(productsService.findById(id));
            cacheData.put(productId, cashedProduct);
//            redisTemplate.opsForValue().set(productId, cashedProduct);
//            System.out.println("кеширование productsRepository");
        }

        return cacheData.get(productId).getProductOptional();
//        return Objects.requireNonNull(redisTemplate.opsForValue().get("productsRepository")).getProductOptional();
    }
//    @Override
//    public ProductDto findById(Long id) {
//        String productId = id.toString();
////        if (!cache.containsKey(productId)) {
//        if (!redisTemplate.hasKey(productId)) {
//            redisTemplate.
//            Object cashedProductDto = new Object();
//            cashedProductDto.setProductDto(productsService.findById(id));
////            cache.put(productId, cashedProductDto);
//            redisTemplate.opsForValue().set(productId, cashedProductDto);
////            System.out.println("кеширование productsRepository");
//        }
//
////        return cache.get(productId).getProductOptional();
//        return Objects.requireNonNull(redisTemplate.opsForValue().get(productId)).getProductDto();
//    }
}
