package com.shop.adapter.out.persistence.adapter;

import com.shop.adapter.out.persistence.mapper.CartItemMapper;
import com.shop.adapter.out.persistence.repository.CartItemRepository;
import com.shop.adapter.out.persistence.repository.dto.CartDetailTuple;
import com.shop.application.port.out.cart.LoadCartItemPort;
import com.shop.domain.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartItemPersistenceAdapter
        implements LoadCartItemPort {

    private final CartItemRepository cartItemRepository;

    @Override
    public Optional<CartItem> loadByCartIdAndItemId(Long cartId, Long itemId) {
        return cartItemRepository.findByCartIdAndItemId(cartId, itemId)
                .map(CartItemMapper::mapToDomain);
    }

    @Override
    public List<CartDetailTuple> loadAllCartDetailDtoList(Long cartId) {
        return cartItemRepository.findCartDetailDtoList(cartId);
    }

}
