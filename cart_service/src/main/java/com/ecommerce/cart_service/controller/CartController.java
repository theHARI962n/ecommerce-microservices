package com.ecommerce.cart_service.controller;


import com.ecommerce.cart_service.dto.AddToCartRequest;
import com.ecommerce.cart_service.dto.UpdateQuantityRequest;
import com.ecommerce.cart_service.entity.Cart;
import com.ecommerce.cart_service.service.CartService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/cart")
public class CartController {


    private final CartService cartService;


    public CartController(
            CartService cartService
    ){
        this.cartService = cartService;
    }



    @PostMapping("/add")
    public ResponseEntity<?> addToCart(

            @RequestBody AddToCartRequest request,

            @RequestHeader("X-User-Email")
            String email

    ){

        Cart cart =
                cartService.addToCart(
                        request,
                        email
                );


        return ResponseEntity.ok(cart);

    }

    @GetMapping
    public ResponseEntity<?> getCart(

            @RequestHeader("X-User-Email")
            String email

    ){

        return ResponseEntity.ok(
                cartService.getCart(email)
        );

    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(

            @RequestHeader("X-User-Email")
            String email

    ){

        return ResponseEntity.ok(
                cartService.checkout(email)
        );

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> removeItem(

            @PathVariable UUID productId,

            @RequestHeader("X-User-Email")
            String email

    ) {

        cartService.removeItem(
                email,
                productId
        );

        return ResponseEntity.ok(
                "Item removed from cart"
        );
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateQuantity(

            @PathVariable UUID productId,

            @RequestBody UpdateQuantityRequest request,

            @RequestHeader("X-User-Email")
            String email

    ) {

        cartService.updateQuantity(
                email,
                productId,
                request.getQuantity()
        );

        return ResponseEntity.ok(
                "Quantity updated"
        );
    }

}