package com.shop.adapter.out.persistence.mapper;

import com.shop.adapter.out.persistence.OrderEntity;
import com.shop.domain.Order;
import org.springframework.util.ObjectUtils;

public class OrderMapper {

    /**
     * 연관 관계
     * - 회원 테이블
     * - 주문 아이템 테이블
     * <br/>
     * 도메인 객체를 엔티티 객체로 변환
     * @param order 주문 도메인 객체
     * */
    public static OrderEntity mapToEntity(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .member(!ObjectUtils.isEmpty(order.getMember()) ? MemberMapper.mapToEntity(order.getMember()) : null)
                .orderItems(order.getOrderItems().stream().map(OrderItemMapper::mapToEntity).toList())
                .build();
    }

    /**
     * 엔티티 객체를 도메인 객체로 변환
     * @param entity 주문 엔티티 객체
     * */
    public static Order mapToDomain(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .orderDate(entity.getOrderDate())
                .orderStatus(entity.getOrderStatus())
                .regTime(entity.getRegTime())
                .updateTime(entity.getUpdateTime())
                .build();
    }

    /**
     * 연관 관계
     * - 회원 테이블
     * <br/>
     * 엔티티 객체를 도메인 객체로 변환
     * @param entity 주문 엔티티 객체
     * */
    public static Order mapToDomainWithMember(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .orderDate(entity.getOrderDate())
                .orderStatus(entity.getOrderStatus())
                .regTime(entity.getRegTime())
                .updateTime(entity.getUpdateTime())
                .member(MemberMapper.mapToDomain(entity.getMember()))
                .build();
    }

    /**
     * 연관 관계
     * - 주문 상품 테이블
     * <br/>
     * 엔티티 객체를 도메인 객체로 변환
     * @param entity 주문 엔티티 객체
     * */
    public static Order mapToDomainWithOrderItems(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .orderDate(entity.getOrderDate())
                .orderStatus(entity.getOrderStatus())
                .regTime(entity.getRegTime())
                .updateTime(entity.getUpdateTime())
                .orderItems(entity.getOrderItems().stream().map(OrderItemMapper::mapToDomainWithItem).toList())
                .build();
    }
}
