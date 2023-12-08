package com.shop.application.port.out.item;

import com.shop.domain.ItemImage;

import java.util.List;

public interface SaveItemImagePort {
        void saveItemImage(List<ItemImage> itemImages);
}
