package com.shop.application.dto;

import com.shop.adapter.out.persistence.repository.dto.CartDetailTuple;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDetailDto {

    private Long cartItemId; //장바구니 상품 아이디

    private String itemNm; //상품명

    private int price; //상품 금액

    private int count; //수량

    private String imgUrl; //상품 이미지 경로

    @Builder
    public CartDetailDto(Long cartItemId, String itemNm, int price, int count, String imgUrl){
        this.cartItemId = cartItemId;
        this.itemNm = itemNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }

    public CartDetailDto(CartDetailTuple tuple){
        this.cartItemId = tuple.getCartItemId();
        this.itemNm = tuple.getItemNm();
        this.price = tuple.getPrice();
        this.count = tuple.getCount();
        this.imgUrl = tuple.getImgUrl();
    }


}