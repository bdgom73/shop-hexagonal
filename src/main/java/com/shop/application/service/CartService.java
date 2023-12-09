package com.shop.application.service;

import com.shop.application.dto.CartDetailDto;
import com.shop.application.dto.OrderDto;
import com.shop.application.dto.request.CartItemRequest;
import com.shop.application.event.OrderEvent;
import com.shop.application.port.in.cart.EditCartUseCase;
import com.shop.application.port.in.cart.GetCartUseCase;
import com.shop.application.port.in.cart.ValidateCartUseCase;
import com.shop.application.port.out.cart.CommandCartItemPort;
import com.shop.application.port.out.cart.CommandCartPort;
import com.shop.application.port.out.cart.LoadCartItemPort;
import com.shop.application.port.out.cart.LoadCartPort;
import com.shop.application.port.out.item.LoadItemPort;
import com.shop.application.port.out.member.LoadMemberPort;
import com.shop.domain.Cart;
import com.shop.domain.CartItem;
import com.shop.domain.Item;
import com.shop.domain.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService
        implements EditCartUseCase,
        GetCartUseCase,
        ValidateCartUseCase {

    private final LoadCartPort loadCartPort;
    private final CommandCartPort commandCartPort;
    private final LoadCartItemPort loadCartItemPort;
    private final CommandCartItemPort commandCartItemPort;
    private final LoadMemberPort loadMemberPort;
    private final LoadItemPort loadItemPort;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public Long addCart(CartItemRequest request, String email) {
        Item item = loadItemPort.loadById(request.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = loadMemberPort.loadByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        Cart cart = loadCartPort.loadByMemberId(member.getId())
                .orElseGet(() -> commandCartPort.save(Cart.createCart(member)));

        Optional<CartItem> savedCartItem = loadCartItemPort.loadByCartIdAndItemId(cart.getId(), item.getId());

        CartItem cartItem;
        if(savedCartItem.isEmpty()){
            cartItem = CartItem.createCartItem(cart, item, request.getCount());
            return commandCartItemPort.save(cartItem)
                    .getId();
        }

        cartItem = savedCartItem.get();
        cartItem.addCount(request.getCount());
        return commandCartItemPort.updateCount(cartItem)
                    .getId();
    }

    @Override
    @Transactional
    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = loadCartItemPort.loadById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItem.setCount(count);
        commandCartItemPort.updateCount(cartItem);
    }

    @Override
    @Transactional
    public void deleteCartItem(Long cartItemId) {
        commandCartItemPort.delete(cartItemId);
    }

    @Override
    public List<CartDetailDto> getCartList(String email) {
        Member member = loadMemberPort.loadByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        Optional<Cart> findCart = loadCartPort.loadByMemberId(member.getId());

        if(findCart.isEmpty()){
            return new ArrayList<>();
        }

        Cart cart = findCart.get();

        return loadCartItemPort.loadAllCartDetailDtoList(cart.getId())
                .stream()
                .map(CartDetailDto::new)
                .toList();
    }

    @Override
    public boolean validateCartItem(Long cartItemId, String email) {
        Member curMember = loadMemberPort.loadByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        CartItem cartItem = loadCartItemPort.loadByIdWithAll(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        Member savedMember = cartItem.getCart().getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    @Override
    @Transactional
    public void orderCartItem(List<Long> cartItemIds, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();

        Map<Long, CartItem> cartItemMap = loadCartItemPort.loadAllByIdWithAll(cartItemIds)
                .stream()
                .collect(Collectors.toMap(
                        CartItem::getId,
                        _this -> _this
                ));

        for (Long cartItemId : cartItemIds) {
            Optional<CartItem> findCartItem =
                    Optional.ofNullable(cartItemMap.get(cartItemId));

            if (findCartItem.isEmpty()) {
                continue;
            }

            orderDtoList.add(new OrderDto(findCartItem.get()));
        }

        publisher.publishEvent(new OrderEvent(email, orderDtoList));

        commandCartItemPort.deleteAllById(cartItemIds);
    }

}
