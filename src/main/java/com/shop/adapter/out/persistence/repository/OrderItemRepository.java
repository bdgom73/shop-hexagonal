package com.shop.adapter.out.persistence.repository;


import com.shop.adapter.out.persistence.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    @Query("SELECT oi FROM OrderItemEntity oi JOIN FETCH oi.item WHERE oi.order.id = :orderId")
    List<OrderItemEntity> findAllByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT oi FROM OrderItemEntity oi JOIN FETCH oi.item JOIN FETCH oi.order WHERE oi.order.id IN :orderIds")
    List<OrderItemEntity> findAllByOrderIds(@Param("orderIds") List<Long> orderIds);
}