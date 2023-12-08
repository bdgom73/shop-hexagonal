package com.shop.adapter.out.persistence.adapter;

import com.shop.adapter.out.persistence.mapper.OrderItemMapper;
import com.shop.adapter.out.persistence.repository.OrderItemRepository;
import com.shop.application.port.out.order.item.LoadOrderItemPort;
import com.shop.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderItemPersistenceAdapter
        implements LoadOrderItemPort {

    private final OrderItemRepository orderItemRepository;


    @Override
    public List<OrderItem> loadAllByOrder(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId)
                .stream()
                .map(OrderItemMapper::mapToDomainWithItem)
                .toList();
    }

    @Override
    public List<OrderItem> loadAllByOrders(List<Long> orderIds) {
        return orderItemRepository.findAllByOrderIds(orderIds)
                .stream()
                .map(OrderItemMapper::mapToDomainWithOrderAndItem)
                .toList();
    }
}
