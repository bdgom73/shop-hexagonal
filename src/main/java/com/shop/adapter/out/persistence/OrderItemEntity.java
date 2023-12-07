package com.shop.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="orders_item")
public class OrderItemEntity extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    private int orderPrice; //주문가격

    private int count; //수량

    @Builder
    public OrderItemEntity(Long id, ItemEntity item, OrderEntity order, int orderPrice, int count) {
        this.id = id;
        this.item = item;
        this.order = order;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public int getTotalPrice(){
        return orderPrice * count;
    }

    public void cancel() {
        this.item.addStock(count);
    }

    public void sell() {
        this.item.removeStock(this.count);
    }

}