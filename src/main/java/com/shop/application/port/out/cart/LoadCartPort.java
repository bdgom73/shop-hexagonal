package com.shop.application.port.out.cart;

import com.shop.domain.Cart;

import java.util.Optional;

public interface LoadCartPort {

    Optional<Cart> loadByMemberId(Long memberId);

}
