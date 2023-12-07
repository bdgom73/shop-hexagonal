package com.shop.domain;

import com.shop.adapter.out.constant.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Order {
    private Long id;
    private Member member;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private List<OrderItem> orderItems = new ArrayList<>();
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    @Builder
    public Order(Long id, Member member, LocalDateTime orderDate, OrderStatus orderStatus, List<OrderItem> orderItems, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id = id;
        this.member = member;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }
}