package com.shop.application.port.in.order.item;

import com.shop.domain.OrderItem;

import java.util.List;

public interface GetOrderItemUseCase {
    List<OrderItem> getOrderItemsByOrder(Long orderId);
}
