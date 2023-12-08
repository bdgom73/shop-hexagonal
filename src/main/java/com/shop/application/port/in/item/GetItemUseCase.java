package com.shop.application.port.in.item;

import com.shop.application.dto.HomeItemDto;
import com.shop.application.dto.request.ItemSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetItemUseCase {
    Page<HomeItemDto> getMainItemPage(ItemSearchRequest request, Pageable pageable);
}
