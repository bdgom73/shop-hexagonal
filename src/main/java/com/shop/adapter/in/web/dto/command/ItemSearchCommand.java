package com.shop.adapter.in.web.dto.command;

import com.shop.adapter.out.constant.ItemSellStatus;
import com.shop.application.dto.request.ItemSearchRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchCommand {

    private String searchDateType;

    private ItemSellStatus searchSellStatus;

    private String searchBy;

    private String searchQuery = "";

    public ItemSearchRequest toRequest() {
        return ItemSearchRequest.builder()
                .searchDateType(searchDateType)
                .searchQuery(searchQuery)
                .searchSellStatus(searchSellStatus)
                .searchBy(searchBy)
                .build();
    }
}
