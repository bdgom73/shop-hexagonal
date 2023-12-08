package com.shop.adapter.out.persistence.repository.dto;

import com.shop.adapter.out.constant.ItemSellStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    private String searchDateType;

    private ItemSellStatus searchSellStatus;

    private String searchBy;

    private String searchQuery = "";

    @Builder
    public ItemSearchDto(String searchDateType, ItemSellStatus searchSellStatus, String searchBy, String searchQuery) {
        this.searchDateType = searchDateType;
        this.searchSellStatus = searchSellStatus;
        this.searchBy = searchBy;
        this.searchQuery = searchQuery;
    }
}