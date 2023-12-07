package com.shop.adapter.out.repository;


import com.shop.adapter.out.persistence.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}