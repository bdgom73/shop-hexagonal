package com.shop.application.dto;

import com.shop.adapter.out.persistence.repository.dto.MainItemDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class HomeItemDto {

    private Long id;

    private String itemNm;

    private String itemDetail;

    private String imgUrl;

    private Integer price;

    public HomeItemDto(MainItemDto mainItemDto) {
        this.id = mainItemDto.getId();
        this.itemNm = mainItemDto.getItemNm();
        this.itemDetail = mainItemDto.getItemDetail();
        this.imgUrl = mainItemDto.getImgUrl();
        this.price = mainItemDto.getPrice();
    }
}