package com.shop.application.port.out.cart;

import com.shop.adapter.out.persistence.repository.dto.CartDetailTuple;
import com.shop.domain.CartItem;

import java.util.List;
import java.util.Optional;

public interface LoadCartItemPort {

    Optional<CartItem> loadByCartIdAndItemId(Long cartId, Long itemId);

    List<CartDetailTuple> loadAllCartDetailDtoList(Long cartId);
}
