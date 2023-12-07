package com.shop.adapter.out.persistence.repository;


import com.shop.adapter.out.persistence.ItemEntity;
import com.shop.adapter.out.persistence.repository.dto.ItemSearchDto;
import com.shop.adapter.out.persistence.repository.dto.MainItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<ItemEntity> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    void updateStockNumber(Long itemId, int stockNumber);
}