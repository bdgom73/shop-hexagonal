package com.shop.adapter.out.persistence.repository;


import com.shop.adapter.out.persistence.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

}