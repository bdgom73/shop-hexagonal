package com.shop.application.port.in.cart;

public interface ValidateCartUseCase {
    boolean validateCartItem(Long cartItemId, String email);
}
