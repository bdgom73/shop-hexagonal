package com.shop.adapter.out.persistence.mapper;

import com.shop.adapter.out.persistence.OrderItemEntity;
import com.shop.domain.OrderItem;

public class OrderItemMapper {

    public static OrderItemEntity mapToEntity(OrderItem orderItem) {
        return OrderItemEntity.builder()
                .id(orderItem.getId())
                .count(orderItem.getCount())
                .orderPrice(orderItem.getOrderPrice())
                .build();
    }

}
