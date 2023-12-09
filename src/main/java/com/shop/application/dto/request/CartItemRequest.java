package com.shop.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemRequest {
    private Long itemId;
    private int count;

    @Builder
    public CartItemRequest(Long itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }
}