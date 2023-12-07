package com.shop.adapter.out.persistence.adapter;

import com.shop.adapter.out.persistence.ItemEntity;
import com.shop.adapter.out.persistence.mapper.ItemMapper;
import com.shop.adapter.out.persistence.repository.ItemRepository;
import com.shop.application.port.out.item.CommandItemPort;
import com.shop.application.port.out.item.LoadItemPort;
import com.shop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemPersistenceAdapter
        implements LoadItemPort,
        CommandItemPort {

    private final ItemRepository itemRepository;

    @Override
    public Optional<Item> loadById(Long id) {
        return itemRepository.findById(id)
                .map(ItemMapper::mapToDomain);
    }

    @Override
    public Item save(Item item) {
        ItemEntity itemEntity = itemRepository.save(ItemMapper.mapToEntity(item));
        return ItemMapper.mapToDomain(itemEntity);
    }
}
