package com.shop.adapter.out.persistence.mapper;

import com.shop.adapter.out.persistence.OrderItemEntity;
import com.shop.domain.OrderItem;

public class OrderItemMapper {

    /**
     * 도메인 객체를 엔티티 객체로 변환
     * @param orderItem 주문 상품 도메인 객체
     * */
    public static OrderItemEntity mapToEntity(OrderItem orderItem) {
        return OrderItemEntity.builder()
                .id(orderItem.getId())
                .count(orderItem.getCount())
                .orderPrice(orderItem.getOrderPrice())
                .item(ItemMapper.mapToEntity(orderItem.getItem()))
                .build();
    }

    /**
     * 엔티티 객체를 도메인 객체로 변환
     * @param entity 주문 상품 엔티티 객체
     * */
    public static OrderItem mapToDomain(OrderItemEntity entity) {
        return OrderItem.builder()
                .id(entity.getId())
                .count(entity.getCount())
                .orderPrice(entity.getOrderPrice())
                .regTime(entity.getRegTime())
                .updateTime(entity.getUpdateTime())
                .build();
    }

    /**
     * 연관 관계
     * - 아이템 테이블
     * <br/>
     * 엔티티 객체를 도메인 객체로 변환
     * @param entity 주문 상품 엔티티 객체
     * */
    public static OrderItem mapToDomainWithItem(OrderItemEntity entity) {
        return OrderItem.builder()
                .id(entity.getId())
                .count(entity.getCount())
                .orderPrice(entity.getOrderPrice())
                .item(ItemMapper.mapToDomain(entity.getItem()))
                .regTime(entity.getRegTime())
                .updateTime(entity.getUpdateTime())
                .build();
    }

    /**
     * 연관 관계
     * - 아이템 테이블
     * - 주문 테이블
     * <br/>
     * 엔티티 객체를 도메인 객체로 변환
     * @param entity 주문 상품 엔티티 객체
     * */
    public static OrderItem mapToDomainWithOrderAndItem(OrderItemEntity entity) {
        return OrderItem.builder()
                .id(entity.getId())
                .count(entity.getCount())
                .orderPrice(entity.getOrderPrice())
                .item(ItemMapper.mapToDomain(entity.getItem()))
                .order(OrderMapper.mapToDomain(entity.getOrder()))
                .regTime(entity.getRegTime())
                .updateTime(entity.getUpdateTime())
                .build();
    }

}
