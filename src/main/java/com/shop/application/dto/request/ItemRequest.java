package com.shop.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemRequest {

    private Long itemId;

    private int count;

    @Builder
    public ItemRequest(Long itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }
}