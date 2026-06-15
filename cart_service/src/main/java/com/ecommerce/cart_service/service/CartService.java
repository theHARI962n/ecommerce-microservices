package com.ecommerce.cart_service.service;


import com.ecommerce.cart_service.client.ProductClient;
import com.ecommerce.cart_service.dto.AddToCartRequest;
import com.ecommerce.cart_service.entity.Cart;
import com.ecommerce.cart_service.entity.CartItem;
import com.ecommerce.cart_service.repository.CartRepository;

import org.springframework.stereotype.Service;


import java.util.ArrayList;



@Service
public class CartService {


    private final CartRepository cartRepository;

    private final ProductClient productClient;



    public CartService(
            CartRepository cartRepository,
            ProductClient productClient
    ){
        this.cartRepository = cartRepository;
        this.productClient = productClient;
    }



    public Cart addToCart(
            AddToCartRequest request,
            String email
    ){


        // check product exists

        var product =
                productClient.getProductById(
                        request.getProductId()
                );


        if(product == null){

            throw new RuntimeException(
                    "Product not found"
            );
        }



        Cart cart =
                cartRepository
                        .findByUserEmail(email)
                        .orElseGet(() -> {


                            Cart newCart = new Cart();

                            newCart.setUserEmail(email);

                            return newCart;

                        });



        CartItem item = new CartItem();


        item.setProductId(
                request.getProductId()
        );


        item.setQuantity(
                request.getQuantity()
        );


        item.setCart(cart);



        cart.getItems().add(item);



        return cartRepository.save(cart);


    }


}
