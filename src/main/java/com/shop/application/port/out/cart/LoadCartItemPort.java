package com.shop.application.port.out.cart;

import com.shop.adapter.out.persistence.repository.dto.CartDetailTuple;
import com.shop.domain.CartItem;

import java.util.List;
import java.util.Optional;

public interface LoadCartItemPort {
    Optional<CartItem> loadById(Long id);
    Optional<CartItem> loadByIdWithAll(Long id);
    Optional<CartItem> loadByCartIdAndItemId(Long cartId, Long itemId);
    List<CartDetailTuple> loadAllCartDetailDtoList(Long cartId);
    List<CartItem> loadAllById(List<Long> ids);

    List<CartItem> loadAllByIdWithAll(List<Long> ids);
}
