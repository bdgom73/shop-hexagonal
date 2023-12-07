package com.shop.domain;

import com.shop.adapter.out.constant.ItemSellStatus;
import com.shop.adapter.out.persistence.ItemEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Item {

    private Long id; //상품 코드

    private String itemNm; //상품명

    private int price; //가격

    private int stockNumber; //재고수량

    private String itemDetail; //상품 상세 설명

    private ItemSellStatus itemSellStatus; //상품 판매 상태

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    public Item(ItemEntity entity) {
        this.id = entity.getId();
        this.itemNm = entity.getItemNm();
        this.price = entity.getPrice();
        this.stockNumber = entity.getStockNumber();
        this.itemDetail = entity.getItemDetail();
        this.itemSellStatus = entity.getItemSellStatus();
        this.regTime = entity.getRegTime();
        this.updateTime = entity.getUpdateTime();
    }
}