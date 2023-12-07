package com.shop.adapter.out.persistence.mapper;

import com.shop.adapter.out.persistence.OrderEntity;
import com.shop.domain.Order;
import org.springframework.util.ObjectUtils;

public class OrderMapper {

    public static OrderEntity mapToEntity(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .member(!ObjectUtils.isEmpty(order.getMember()) ? MemberMapper.mapToEntity(order.getMember()) : null)
                .orderItems(order.getOrderItems().stream().map(OrderItemMapper::mapToEntity).toList())
                .build();
    }

    public static Order mapToDomain(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .orderDate(entity.getOrderDate())
                .orderStatus(entity.getOrderStatus())
                .regTime(entity.getRegTime())
                .updateTime(entity.getUpdateTime())
                .build();
    }

}
