package com.shop.adapter.out.persistence.adapter;

import com.shop.adapter.out.persistence.ItemEntity;
import com.shop.adapter.out.persistence.OrderEntity;
import com.shop.adapter.out.persistence.OrderItemEntity;
import com.shop.adapter.out.persistence.mapper.OrderMapper;
import com.shop.adapter.out.persistence.repository.ItemRepository;
import com.shop.adapter.out.persistence.repository.OrderRepository;
import com.shop.application.port.out.order.CommandOrderPort;
import com.shop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter
        implements CommandOrderPort {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = OrderMapper.mapToEntity(order);
        List<OrderItemEntity> orderItems = orderEntity.getOrderItems();
        // 주문 상품의 원본 아이템 객체의 식별자 리스트 반환
        List<Long> itemIds = orderItems.stream()
                .map(_this -> _this.getItem().getId())
                .toList();

        // 원본 상품 객체 리스트 맵으로 반환
        Map<Long, ItemEntity> itemsMap = itemRepository.findAllById(itemIds).stream()
                .collect(Collectors.toMap(
                        ItemEntity::getId,
                        _this -> _this
                ));

        // 주문 상품 로직
        for (OrderItemEntity orderItem : orderItems) {
            ItemEntity itemEntity = itemsMap.get(orderItem.getItem().getId());
            if (!ObjectUtils.isEmpty(itemEntity)) {
                // 원본 아이템 수량 감소
                itemEntity.removeStock(orderItem.getCount());
            }
            // Order 테이블 추가
            orderItem.setOrder(orderEntity);
        }

        OrderEntity saveOrder = orderRepository.save(orderEntity);

        return OrderMapper.mapToDomain(saveOrder);
    }
}
