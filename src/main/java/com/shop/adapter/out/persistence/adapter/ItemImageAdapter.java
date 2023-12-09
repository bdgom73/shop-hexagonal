package com.shop.adapter.out.persistence.adapter;

import com.shop.adapter.out.persistence.ItemEntity;
import com.shop.adapter.out.persistence.ItemImgEntity;
import com.shop.adapter.out.persistence.mapper.ItemImageMapper;
import com.shop.adapter.out.persistence.repository.ItemImgRepository;
import com.shop.adapter.out.persistence.repository.ItemRepository;
import com.shop.application.port.out.item.LoadItemImagePort;
import com.shop.application.port.out.item.SaveItemImagePort;
import com.shop.domain.ItemImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemImageAdapter
        implements SaveItemImagePort,
        LoadItemImagePort {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImageRepository;

    @Override
    public void saveItemImage(List<ItemImage> itemImages) {
        if (itemImages.isEmpty()) {
            throw new IllegalArgumentException("상품 이미지가 존재하지 않습니다.");
        }

        ItemEntity itemEntity = itemRepository.findById(itemImages.get(0).getItem().getId())
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        List<ItemImgEntity> list = itemImages.stream().map((element) -> ItemImageMapper.mapToEntity(element, itemEntity)).toList();
        itemImageRepository.saveAll(list);
    }

    @Override
    public List<ItemImage> loadAllByItemIdOrderByIdAsc(Long itemId) {
        return itemImageRepository.findByItemIdOrderByIdAsc(itemId)
                .stream()
                .map(ItemImageMapper::mapToDomain)
                .toList();
    }

}
