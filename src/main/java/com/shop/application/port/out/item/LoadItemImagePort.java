package com.shop.application.port.out.item;

import com.shop.domain.ItemImage;

import java.util.List;

public interface LoadItemImagePort {
        List<ItemImage> loadAllByItemIdOrderByIdAsc(Long itemId);
}
