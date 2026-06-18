package com.ecommerce.cart_service.service;


import com.ecommerce.cart_service.client.OrderClient;
import com.ecommerce.cart_service.client.ProductClient;
import com.ecommerce.cart_service.dto.*;
import com.ecommerce.cart_service.entity.Cart;
import com.ecommerce.cart_service.entity.CartItem;
import com.ecommerce.cart_service.repository.CartRepository;

import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class CartService {


    private final CartRepository cartRepository;

    private final ProductClient productClient;
    private final OrderClient orderClient;


    public CartService(
            CartRepository cartRepository,
            ProductClient productClient,
            OrderClient orderClient
    ){
        this.cartRepository = cartRepository;
        this.productClient = productClient;
        this.orderClient = orderClient;
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



        CartItem existingItem = cart.getItems()
                .stream()
                .filter(item ->
                        item.getProductId().equals(request.getProductId())
                )
                .findFirst()
                .orElse(null);

        if (existingItem != null) {

            existingItem.setQuantity(
                    existingItem.getQuantity() + request.getQuantity()
            );

        } else {

            CartItem item = new CartItem();

            item.setProductId(
                    request.getProductId()
            );

            item.setQuantity(
                    request.getQuantity()
            );

            item.setCart(cart);

            cart.getItems().add(item);
        }
//        CartItem item = new CartItem();
//
//
//        item.setProductId(
//                request.getProductId()
//        );
//
//
//        item.setQuantity(
//                request.getQuantity()
//        );
//
//
//        item.setCart(cart);
//
//
//
//        cart.getItems().add(item);



        return cartRepository.save(cart);


    }


    public CartResponse getCart(String email){


        Cart cart =
                cartRepository
                        .findByUserEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException("Cart not found")
                        );



        List<CartItemResponse> items =
                cart.getItems()
                        .stream()
                        .map(item -> {


                            Map<String,Object> product =
                                    productClient.getProductById(
                                            item.getProductId()
                                    );



                            CartItemResponse response =
                                    new CartItemResponse();


                            response.setProductId(
                                    item.getProductId()
                            );


                            response.setProductName(
                                    product.get("name").toString()
                            );


                            response.setPrice(
                                    new BigDecimal(
                                            product.get("price").toString()
                                    )
                            );


                            response.setQuantity(
                                    item.getQuantity()
                            );


                            return response;


                        })
                        .toList();



        CartResponse response =
                new CartResponse();



        response.setCartId(
                cart.getId()
        );


        response.setUserEmail(
                cart.getUserEmail()
        );


        response.setItems(items);



        return response;

    }



    public OrderResponse checkout(String email){


        Cart cart =
                cartRepository
                        .findByUserEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException("Cart empty")
                        );



        List<OrderItemRequest> items =
                cart.getItems()
                        .stream()
                        .map(cartItem -> {


                            OrderItemRequest item =
                                    new OrderItemRequest();


                            item.setProductId(
                                    cartItem.getProductId()
                            );


                            item.setQuantity(
                                    cartItem.getQuantity()
                            );


                            return item;


                        })
                        .toList();



        OrderRequest orderRequest =
                new OrderRequest();


        orderRequest.setItems(items);


        System.out.println(orderRequest);
        System.out.println(email);

        OrderResponse response =
                orderClient.createOrder(orderRequest,email);



        // clear cart after successful order

        cart.getItems().clear();


        cartRepository.save(cart);



        return response;

    }

    public void removeItem(
            String email,
            UUID productId
    ) {

        Cart cart =
                cartRepository
                        .findByUserEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException("Cart not found")
                        );

        cart.getItems().removeIf(
                item -> item.getProductId().equals(productId)
        );

        cartRepository.save(cart);
    }

    public void updateQuantity(
            String email,
            UUID productId,
            Integer quantity
    ) {

        Cart cart =
                cartRepository
                        .findByUserEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException("Cart not found")
                        );

        if(quantity <= 0){
            throw new RuntimeException(
                    "Quantity must be greater than zero"
            );
        }

        CartItem item =
                cart.getItems()
                        .stream()
                        .filter(cartItem ->
                                cartItem.getProductId().equals(productId)
                        )
                        .findFirst()
                        .orElseThrow(
                                () -> new RuntimeException("Item not found")
                        );

        item.setQuantity(quantity);

        cartRepository.save(cart);
    }


}
