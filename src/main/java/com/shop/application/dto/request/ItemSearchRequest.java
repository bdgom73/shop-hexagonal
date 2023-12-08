package com.shop.application.dto.request;

import com.shop.adapter.out.constant.ItemSellStatus;
import com.shop.adapter.out.persistence.repository.dto.ItemSearchDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchRequest {

    private String searchDateType;

    private ItemSellStatus searchSellStatus;

    private String searchBy;

    private String searchQuery = "";

    @Builder
    public ItemSearchRequest(String searchDateType, ItemSellStatus searchSellStatus, String searchBy, String searchQuery) {
        this.searchDateType = searchDateType;
        this.searchSellStatus = searchSellStatus;
        this.searchBy = searchBy;
        this.searchQuery = searchQuery;
    }

    public ItemSearchDto toDto() {
        return ItemSearchDto.builder()
                .searchDateType(searchDateType)
                .searchQuery(searchQuery)
                .searchSellStatus(searchSellStatus)
                .searchBy(searchBy).build();
    }
}
