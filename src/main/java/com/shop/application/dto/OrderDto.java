package com.shop.application.dto;

import com.shop.domain.CartItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.ObjectUtils;

@Getter
@Setter
@ToString
public class OrderDto {
    private Long itemId;
    private int count;

    public OrderDto(Long itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }

    public OrderDto(CartItem cartItem) {
        this.itemId = !ObjectUtils.isEmpty(cartItem.getItem()) ? cartItem.getItem().getId() : null;
        this.count = cartItem.getCount();
    }
}
