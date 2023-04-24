package com.grekoff.market.core.proxy;


import com.grekoff.market.core.entities.Product;
import com.grekoff.market.core.event.ChangedDBProductsEvent;
import com.grekoff.market.core.listener.Listener;
import com.grekoff.market.core.services.ProductsService;
import jakarta.annotation.PostConstruct;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.stereotype.Service;


import java.io.*;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersProductsServiceProxy implements UsersProductsService, Listener {
    private final ProductsService productsService;
    private RedisTemplate<String, Object> redisTemplate;
    private final ByteArraySerializer byteArraySerializer;
    @Autowired
    private final JedisConnectionFactory jedisConnectionFactory;

    @PostConstruct
    public void init(){
        this.redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.afterPropertiesSet();
    }


    @Override
    public void onEventReceived(ChangedDBProductsEvent event) {
        log.info(event.getMessage());
        try {
            if (Boolean.TRUE.equals(redisTemplate.hasKey("cache"))) {
                Map<String, byte[]> cache = (Map<String, byte[]>) redisTemplate.opsForValue().get("cache");
                assert cache != null;
                cache.clear();
                redisTemplate.opsForValue().set("cache", cache);
            }
        } catch (Exception e) {
            //  e.printStackTrace();
            log.warn(String.valueOf(e));
        }
    }


    public Map<String, byte[]> getCache(String cacheId) {
        if(Boolean.FALSE.equals(redisTemplate.hasKey(cacheId))){
            Map<String, byte[]> cache = new HashMap<>();
            redisTemplate.opsForValue().set(cacheId,  cache);
        }
        return (Map<String, byte[]>) redisTemplate.opsForValue().get(cacheId);
    }

    @Override
    public List<Product> findAll() {
        List<Product> result;

        try {
            Map<String, byte[]> cache = getCache("cache");
            if (cache != null) {
                if (!cache.containsKey("findAll")) {
                    cache.put("findAll", byteArraySerializer.serialize(productsService.findAll()));
                    redisTemplate.opsForValue().set("cache", cache);
                }
                byte[] productListBytes = (byte[]) ((Map<String, byte[]>) redisTemplate.opsForValue().get("cache")).get("findAll");
                List<Product> productListFromCache = (List<Product>) byteArraySerializer.deserialize(productListBytes);
                return productListFromCache;
            }
            List<Product> productList =  productsService.findAll();
            result = productList;
        } catch (Exception e) {
            //  e.printStackTrace();
            log.error(String.valueOf(e));
            List<Product> productList =  productsService.findAll();
            result = productList;
        }
        return result;
    }

    @Override
    public Page<Product> findAllPages(Integer minPrice, Integer maxPrice, String partTitle, Integer offset, Integer size, Boolean first, Boolean last, Integer currentPage) {
        String currentPageValue;
        Page<Product> result;

        currentPageValue = (minPrice + "_" + maxPrice + "_" + partTitle + "_" + offset + "_" + size + "_" + first + "_" + last + "_" + currentPage).toString();
        try {
            Map<String, byte[]> cache = getCache("cache");
            if (cache != null) {
                if (!cache.containsKey(currentPageValue)) {
                    Page<Product> pageProduct = productsService.findAllPages(minPrice, maxPrice, partTitle, offset, size, first, last, currentPage);
                    cache.put(currentPageValue, byteArraySerializer.serialize(pageProduct));
                    redisTemplate.opsForValue().set("cache", cache);
                }
                byte[] pageProductsBytes = (byte[]) ((Map<String, byte[]>) Objects.requireNonNull(redisTemplate.opsForValue().get("cache"))).get(currentPageValue);
                Page<Product> pageProductFromCache = (Page<Product>) byteArraySerializer.deserialize(pageProductsBytes);
                return pageProductFromCache;
            }
            Page<Product> pageProduct = productsService.findAllPages(minPrice, maxPrice, partTitle, offset, size, first, last, currentPage);
            result = pageProduct;
        } catch (Exception e) {
            //  e.printStackTrace();
            log.error(String.valueOf(e));
            Page<Product> pageProduct = productsService.findAllPages(minPrice, maxPrice, partTitle, offset, size, first, last, currentPage);
            result = pageProduct;
        }
        return result;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String productId = id.toString();
        Optional<Product> result;

        try {
            Map<String, byte[]> cache = getCache("cache");
            if (cache != null) {
                if (!cache.containsKey(productId)) {
                    cache.put(productId, byteArraySerializer.serialize(productsService.findById(id).get()));
                    redisTemplate.opsForValue().set("cache", cache);
                }
                byte[] productOptionalBytes = (byte[]) ((Map<String, byte[]>) redisTemplate.opsForValue().get("cache")).get(productId);
                Product productFromCache = (Product) byteArraySerializer.deserialize(productOptionalBytes);
                return Optional.ofNullable(productFromCache);
            }
            Optional<Product> productOptional = Optional.of(productsService.findById(id).get());
            result = productOptional;
        } catch (Exception e) {
            //  e.printStackTrace();
            log.error(String.valueOf(e));
            Optional<Product> productOptional = Optional.of(productsService.findById(id).get());
            result = productOptional;
        }
        return result;
    }
}
