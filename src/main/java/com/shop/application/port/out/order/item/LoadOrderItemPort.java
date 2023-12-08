package com.shop.application.port.out.order.item;

import com.shop.domain.OrderItem;

import java.util.List;

public interface LoadOrderItemPort {

    List<OrderItem> loadAllByOrder(Long orderId);

    List<OrderItem> loadAllByOrders(List<Long> orderIds);


}
