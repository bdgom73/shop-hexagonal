package com.shop.adapter.out.persistence.mapper;

import com.shop.adapter.out.persistence.ItemEntity;
import com.shop.domain.Item;

public class ItemMapper {

    public static ItemEntity mapToEntity(Item item) {
        return ItemEntity.builder()
                .id(item.getId())
                .itemNm(item.getItemNm())
                .itemDetail(item.getItemDetail())
                .itemSellStatus(item.getItemSellStatus())
                .price(item.getPrice())
                .stockNumber(item.getStockNumber())
                .build();
    }

    public static Item mapToDomain(ItemEntity entity) {
        return Item.builder()
                .id(entity.getId())
                .itemNm(entity.getItemNm())
                .price(entity.getPrice())
                .stockNumber(entity.getStockNumber())
                .itemDetail(entity.getItemDetail())
                .itemSellStatus(entity.getItemSellStatus())
                .regTime(entity.getRegTime())
                .updateTime(entity.getUpdateTime())
                .build();

    }
}
