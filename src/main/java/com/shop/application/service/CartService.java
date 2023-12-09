package com.shop.application.service;

import com.shop.application.dto.CartDetailDto;
import com.shop.application.port.in.cart.GetCartUseCase;
import com.shop.application.port.out.cart.LoadCartItemPort;
import com.shop.application.port.out.cart.LoadCartPort;
import com.shop.application.port.out.member.LoadMemberPort;
import com.shop.domain.Cart;
import com.shop.domain.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService
        implements GetCartUseCase {

    private final LoadCartPort loadCartPort;
    private final LoadCartItemPort loadCartItemPort;
    private final LoadMemberPort loadMemberPort;

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
}
