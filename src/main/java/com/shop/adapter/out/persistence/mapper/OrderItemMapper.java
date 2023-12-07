package com.shop.adapter.out.persistence.mapper;

import com.shop.adapter.out.persistence.OrderItemEntity;
import com.shop.domain.OrderItem;
import org.springframework.util.ObjectUtils;

public class OrderItemMapper {

    public static OrderItemEntity mapToEntity(OrderItem orderItem) {
        return OrderItemEntity.builder()
                .id(orderItem.getId())
                .count(orderItem.getCount())
                .orderPrice(orderItem.getOrderPrice())
                .item(!ObjectUtils.isEmpty(orderItem.getItem()) ? ItemMapper.mapToEntity(orderItem.getItem()) : null)
                .build();
    }

    public static OrderItem mapToDomain(OrderItemEntity entity) {
        return OrderItem.builder()
                .id(entity.getId())
                .count(entity.getCount())
                .orderPrice(entity.getOrderPrice())
                .item(ItemMapper.mapToDomain(entity.getItem()))
                .regTime(entity.getRegTime())
                .updateTime(entity.getUpdateTime())
                .build();
    }

}
