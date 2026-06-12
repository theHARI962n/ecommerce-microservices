package com.ecommerce.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@FeignClient (name = "product-service", url = "${product.service.url}")
public interface ProductClient {
    @GetMapping("/api/products/{id}")
    Map<String, Object> getProductById(@PathVariable UUID id);

    @PutMapping("/api/products/{id}/reduce-stock")
    void reduceStock(@PathVariable UUID id,
                     @RequestParam Integer quantity);
}
