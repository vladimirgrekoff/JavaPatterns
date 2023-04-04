package com.grekoff.market.api.cart.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import com.grekoff.market.api.cart.properties.ProductsServiceIntegrationProperties;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ProductsServiceIntegrationProperties.class)
public class AppConfig {
    private final ProductsServiceIntegrationProperties productsServiceIntegrationProperties;


    @Bean
    public WebClient productServiceWebClient() {
//        HttpClient.create();
//        return WebClient
//                .builder()
//                .baseUrl(productsServiceIntegrationProperties.getUrl())
//                .clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection()
//                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, productsServiceIntegrationProperties.getConnectTimeout())
//                        .doOnConnected(connection -> {
//                            connection.addHandlerLast(new ReadTimeoutHandler(productsServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
//                            connection.addHandlerLast(new WriteTimeoutHandler(productsServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
//                        })))
//                .build();

    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, productsServiceIntegrationProperties.getConnectTimeout())
            .doOnConnected(connection ->
                    connection.addHandlerLast(new ReadTimeoutHandler(productsServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(productsServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS)));

    return WebClient.builder()
            .baseUrl(productsServiceIntegrationProperties.getUrl())
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();

    }
}
