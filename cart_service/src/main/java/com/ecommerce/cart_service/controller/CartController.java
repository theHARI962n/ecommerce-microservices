package com.ecommerce.cart_service.controller;


import com.ecommerce.cart_service.dto.AddToCartRequest;
import com.ecommerce.cart_service.entity.Cart;
import com.ecommerce.cart_service.service.CartService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



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

}