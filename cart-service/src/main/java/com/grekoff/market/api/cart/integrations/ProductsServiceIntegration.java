package com.grekoff.market.api.cart.integrations;

import com.grekoff.market.api.core.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final WebClient productsServiceWebClient;

    public ProductDto findById(Long id) {
        return productsServiceWebClient.get()
                .uri("/api/v1/products/" + id)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }
}
