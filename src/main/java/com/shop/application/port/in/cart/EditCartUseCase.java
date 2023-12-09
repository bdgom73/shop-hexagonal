package com.shop.application.port.in.cart;

import com.shop.application.dto.request.CartItemRequest;

import java.util.List;

public interface EditCartUseCase {
    Long addCart(CartItemRequest request, String email);

    void updateCartItemCount(Long cartItemId, int count);
    void deleteCartItem(Long cartItemId);

    void orderCartItem(List<Long> cartItemIds, String email);
}
