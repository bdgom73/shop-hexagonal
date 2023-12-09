package com.shop.application.dto.request;

import com.shop.adapter.in.web.dto.ItemImgDto;
import com.shop.adapter.out.constant.ItemSellStatus;
import com.shop.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ItemFormRequest {

    private Long id;

    private String itemNm;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    @Builder
    public ItemFormRequest(Long id, String itemNm, Integer price, String itemDetail, Integer stockNumber, ItemSellStatus itemSellStatus, List<Long> itemImgIds) {
        this.id = id;
        this.itemNm = itemNm;
        this.price = price;
        this.itemDetail = itemDetail;
        this.stockNumber = stockNumber;
        this.itemSellStatus = itemSellStatus;
        this.itemImgIds = itemImgIds;
    }

    public Item toDomain() {
        return Item.builder()
                .id(id)
                .itemNm(itemNm)
                .price(price)
                .itemDetail(itemDetail)
                .stockNumber(stockNumber)
                .itemSellStatus(itemSellStatus)
                .build();
    }
}