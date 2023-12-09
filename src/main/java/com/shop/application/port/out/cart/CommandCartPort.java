package com.shop.application.port.out.cart;

import com.shop.domain.Cart;

public interface CommandCartPort {
    Cart save(Cart cart);
}
