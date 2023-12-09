package com.shop.adapter.out.persistence.adapter;

import com.shop.adapter.out.persistence.CartEntity;
import com.shop.adapter.out.persistence.mapper.CartMapper;
import com.shop.adapter.out.persistence.repository.CartRepository;
import com.shop.application.port.out.cart.CommandCartPort;
import com.shop.application.port.out.cart.LoadCartPort;
import com.shop.domain.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartPersistenceAdapter
        implements CommandCartPort,
        LoadCartPort {

    private final CartRepository cartRepository;

    @Override
    public Optional<Cart> loadByMemberId(Long memberId) {
        return cartRepository.findByMemberId(memberId)
                .map(CartMapper::mapToDomainWithMember);
    }

    @Override
    public Cart save(Cart cart) {
        CartEntity cartEntity = cartRepository.save(CartMapper.mapToEntity(cart));
        return CartMapper.mapToDomainWithMember(cartEntity);
    }
}
