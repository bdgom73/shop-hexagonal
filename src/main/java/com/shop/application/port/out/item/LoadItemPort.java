package com.shop.application.port.out.item;

import com.shop.domain.Item;

import java.util.List;
import java.util.Optional;

public interface LoadItemPort {
    Optional<Item> loadById(Long id);

    List<Item> loadAllByIn(List<Long> ids);
}
