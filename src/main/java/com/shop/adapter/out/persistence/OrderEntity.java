package com.shop.adapter.out.persistence;

import com.shop.adapter.out.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor
public class OrderEntity extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
            , orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItemEntity orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    @Builder
    public OrderEntity(Long id, MemberEntity member, LocalDateTime orderDate, OrderStatus orderStatus, List<OrderItemEntity> orderItems) {
        this.id = id;
        this.member = member;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderItems = ObjectUtils.isEmpty(orderItems) ? new ArrayList<>() : orderItems;
    }

    public static OrderEntity createOrder(MemberEntity member, List<OrderItemEntity> orderItemList) {
        OrderEntity order = new OrderEntity();
        order.setMember(member);

        for(OrderItemEntity orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItemEntity orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
        for (OrderItemEntity orderItem : orderItems) {
            orderItem.cancel();
        }
    }

}