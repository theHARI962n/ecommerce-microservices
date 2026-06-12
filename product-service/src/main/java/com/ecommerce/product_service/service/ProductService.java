package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.ProductRequest;
import com.ecommerce.product_service.dto.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.exception.InsufficientStockException;
import com.ecommerce.product_service.exception.ProductNotFoundException;
import com.ecommerce.product_service.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger =
            LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {

        logger.info("Creating product name={} price={}",
                request.getName(), request.getPrice());

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product savedProduct = productRepository.save(product);
        logger.info("Product created successfully productId={}", savedProduct.getId());
        return mapToResponse(savedProduct);


    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    // Pagination and sorting
    public Page<ProductResponse> getProducts(Pageable pageable) {

        logger.info("Fetching products page={} size={} sort={}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort());

        return productRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public ProductResponse getProductById(UUID id) {
        logger.info("Fetching product with id={}", id);
        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
                .orElseThrow(() ->{
                    logger.error("Product not found with id={}", id);
                    return new ProductNotFoundException("Product not found");
                });

        return mapToResponse(product);
    }

    public void reduceStock(UUID productId, Integer quantity) {

        logger.info("Reducing stock for productId={} quantity={}",
                productId, quantity);

        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));


        if (product.getStock() < quantity) {
            logger.warn("Insufficient stock for productId={} requested={} available={}",
                    productId, quantity, product.getStock());
//            throw new RuntimeException("Insufficient stock");
            throw new InsufficientStockException("Insufficient stock");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        logger.info("Stock updated for productId={} remainingStock={}",
                productId, product.getStock());
    }

    // update product
    public ProductResponse updateProduct(UUID id, ProductRequest request) {

        logger.info("Updating product with id={}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product is not found with id={}", id);
                    return new ProductNotFoundException("Product not found");
                });

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product updatedProduct = productRepository.save(product);

        logger.info("Product updated successfully productId={}", updatedProduct.getId());

        return mapToResponse(updatedProduct);
    }

    //delete product
    public void deleteProduct(UUID id) {

        logger.info("Deleting product with id={}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product has not been found with id={}", id);
                    return new ProductNotFoundException("Product not found");
                });

        productRepository.delete(product);

        logger.info("Product deleted successfully productId={}", id);
    }


    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        return response;
    }

}
