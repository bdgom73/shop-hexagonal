package com.shop.adapter.out.persistence.mapper;

import com.shop.adapter.out.persistence.ItemEntity;
import com.shop.adapter.out.persistence.ItemImgEntity;
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

    public static ItemImage mapToDomain(ItemImgEntity entity) {
        return ItemImage.builder()
                .id(entity.getId())
                .imageName(entity.getImgName())
                .imageUrl(entity.getImgUrl())
                .isRepresentative(entity.getRepimgYn().equals("Y"))
                .originalFileName(entity.getOriImgName())
                .item(ItemMapper.mapToDomain(entity.getItem()))
                .build();
    }
}
