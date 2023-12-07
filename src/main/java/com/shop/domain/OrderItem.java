package com.shop.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter @Setter
public class OrderItem {

    private Long id;
    private Order order;
    private Item item;
    private int orderPrice;
    private int count;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    @Builder
    public OrderItem(Long id, Order order, Item item, int orderPrice, int count, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id = id;
        this.order = order;
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }

    public static OrderItem createOrderItem(Item item, int count){
        return OrderItem.builder()
                .count(count)
                .item(item)
                .orderPrice(item.getPrice())
                .build();
    } 

}