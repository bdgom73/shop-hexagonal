package com.shop.application.port.out.cart;

import com.shop.domain.CartItem;

import java.util.List;

public interface CommandCartItemPort {
    CartItem save(CartItem cartItem);
    CartItem updateCount(CartItem cartItem);
    void delete(Long cartItemId);
    void deleteAllById(List<Long> cartItemIds);
}
