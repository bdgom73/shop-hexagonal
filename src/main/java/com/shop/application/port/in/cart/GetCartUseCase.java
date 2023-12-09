package com.shop.application.port.in.cart;

import com.shop.application.dto.CartDetailDto;

import java.util.List;

public interface GetCartUseCase {
    List<CartDetailDto> getCartList(String email);
}
