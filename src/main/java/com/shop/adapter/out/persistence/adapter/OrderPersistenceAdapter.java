package com.shop.adapter.out.persistence.adapter;

import com.shop.adapter.out.persistence.OrderEntity;
import com.shop.adapter.out.persistence.OrderItemEntity;
import com.shop.adapter.out.persistence.mapper.OrderMapper;
import com.shop.adapter.out.persistence.repository.OrderRepository;
import com.shop.application.port.out.order.CommandOrderPort;
import com.shop.application.port.out.order.LoadOrderPort;
import com.shop.domain.Order;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter
        implements CommandOrderPort,
        LoadOrderPort {

    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = OrderMapper.mapToEntity(order);
        List<OrderItemEntity> orderItems = orderEntity.getOrderItems();

        // 주문 상품 로직
        for (OrderItemEntity orderItem : orderItems) {
            orderItem.setOrder(orderEntity);
        }

        OrderEntity saveOrder = orderRepository.save(orderEntity);

        return OrderMapper.mapToDomain(saveOrder);
    }

    @Override
    public Order cancel(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        orderEntity.cancelOrder();
        return OrderMapper.mapToDomain(orderEntity);
    }

    @Override
    public Optional<Order> loadById(Long id) {
        return orderRepository.findById(id)
                .map(OrderMapper::mapToDomainWithMember);
    }

    @Override
    public List<Order> loadByEmail(String email, Pageable pageable) {
        return orderRepository.findOrders(email, pageable)
                .stream()
                .map(OrderMapper::mapToDomain)
                .toList();
    }

    @Override
    public Long loadOrderCountByEmail(String email) {
        return orderRepository.countOrder(email);
    }

}
