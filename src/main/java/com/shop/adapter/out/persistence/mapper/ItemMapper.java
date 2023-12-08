package com.shop.adapter.out.persistence.mapper;

import com.shop.adapter.out.persistence.ItemEntity;
import com.shop.domain.Item;

public class ItemMapper {

    /**
     * 연관 관계가 없음.
     * 도메인 객체를 엔티티 객체로 변환
     * @param item 아이템 도메인 객체
     * */
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

    /**
     * 엔티티 객체를 도메인 객체로 변환
     * @param entity 아이템 엔티티 객체
     * */
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
