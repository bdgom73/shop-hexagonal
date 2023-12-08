package com.shop.adapter.out.persistence.mapper;

import com.shop.adapter.out.persistence.ItemEntity;
import com.shop.adapter.out.persistence.ItemImgEntity;
import com.shop.domain.Item;
import com.shop.domain.ItemImage;

public class ItemImageMapper {

    public static ItemImgEntity mapToEntity(ItemImage itemImage, ItemEntity itemEntity) {
        return ItemImgEntity.builder()
                .id(itemImage.getId())
                .imgName(itemImage.getImageName())
                .oriImgName(itemImage.getOriginalFileName())
                .imgUrl(itemImage.getImageUrl())
                .repimgYn(itemImage.isRepresentative() ? "Y" : "N")
                .item(itemEntity)
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
