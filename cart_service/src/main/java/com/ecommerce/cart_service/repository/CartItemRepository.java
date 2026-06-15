package com.ecommerce.cart_service.repository;


import com.ecommerce.cart_service.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface CartItemRepository
        extends JpaRepository<CartItem, UUID> {


}