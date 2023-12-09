package com.shop.adapter.out.persistence.adapter;

import com.shop.adapter.out.persistence.CartItemEntity;
import com.shop.adapter.out.persistence.mapper.CartItemMapper;
import com.shop.adapter.out.persistence.repository.CartItemRepository;
import com.shop.adapter.out.persistence.repository.dto.CartDetailTuple;
import com.shop.application.port.out.cart.CommandCartItemPort;
import com.shop.application.port.out.cart.LoadCartItemPort;
import com.shop.domain.CartItem;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartItemPersistenceAdapter
        implements CommandCartItemPort,
        LoadCartItemPort {

    private final CartItemRepository cartItemRepository;

    @Override
    public Optional<CartItem> loadById(Long id) {
        return cartItemRepository.findById(id)
                .map(CartItemMapper::mapToDomain);
    }

    @Override
    public Optional<CartItem> loadByIdWithAll(Long id) {
        return cartItemRepository.findById(id)
                .map(CartItemMapper::mapToDomainWithAll);
    }

    @Override
    public Optional<CartItem> loadByCartIdAndItemId(Long cartId, Long itemId) {
        return cartItemRepository.findByCartIdAndItemId(cartId, itemId)
                .map(CartItemMapper::mapToDomain);
    }

    @Override
    public List<CartDetailTuple> loadAllCartDetailDtoList(Long cartId) {
        return cartItemRepository.findCartDetailDtoList(cartId);
    }

    @Override
    public List<CartItem> loadAllById(List<Long> ids) {
        return cartItemRepository.findAllById(ids)
                .stream()
                .map(CartItemMapper::mapToDomain)
                .toList();
    }

    @Override
    public List<CartItem> loadAllByIdWithAll(List<Long> ids) {
        return cartItemRepository.findAllById(ids)
                .stream()
                .map(CartItemMapper::mapToDomainWithAll)
                .toList();
    }

    @Override
    public CartItem save(CartItem cartItem) {
        CartItemEntity cartItemEntity = cartItemRepository.save(CartItemMapper.mapToEntity(cartItem));
        return CartItemMapper.mapToDomain(cartItemEntity);
    }

    @Override
    public CartItem updateCount(CartItem cartItem) {
        CartItemEntity cartItemEntity = cartItemRepository.findById(cartItem.getId())
                .orElseThrow(EntityNotFoundException::new);
        cartItemEntity.updateCount(cartItem.getCount());
        return CartItemMapper.mapToDomain(cartItemEntity);
    }

    @Override
    public void delete(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void deleteAllById(List<Long> cartItemIds) {
        cartItemRepository.deleteAllById(cartItemIds);
    }
}
