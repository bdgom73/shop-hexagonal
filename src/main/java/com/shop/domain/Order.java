package com.shop.domain;

import com.shop.adapter.out.constant.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.ObjectUtils;

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
    private List<OrderItem> orderItems;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    @Builder
    public Order(Long id, Member member, LocalDateTime orderDate, OrderStatus orderStatus, List<OrderItem> orderItems, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id = id;
        this.member = member;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderItems = ObjectUtils.isEmpty(orderItems) ? new ArrayList<>() : orderItems;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = Order.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .build();


        for(OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }
}