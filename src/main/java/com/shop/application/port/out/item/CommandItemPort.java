package com.shop.application.port.out.item;

import com.shop.domain.Item;

public interface CommandItemPort {
    Item save(Item item);
    void updateStock(Item item);
    Item updateItem(Item item);
}
