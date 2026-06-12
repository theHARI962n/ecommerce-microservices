package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.ProductRequest;
import com.ecommerce.product_service.dto.ProductResponse;
import com.ecommerce.product_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create product
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get all products
//    @GetMapping
//    public ResponseEntity<List<ProductResponse>> getAllProducts() {
//        return ResponseEntity.ok(productService.getAllProducts());
//    }

    // Get all products (Pagination and sorting)
    // We implemented pagination using Spring Pageable and
    // also added safety defaults like max page size to prevent large data loads and improve performance.
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(Pageable pageable) {

        return ResponseEntity.ok(
                productService.getProducts(pageable)
        );
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // Reduce stock (service - service)
    @PutMapping("/{id}/reduce-stock")
    public ResponseEntity<Void> reduceStock(
            @PathVariable UUID id,
            @Valid @RequestParam Integer quantity
    ) {
        productService.reduceStock(id, quantity);
        return ResponseEntity.ok().build();
    }

    // Update product
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequest request
    ) {
        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    // Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

