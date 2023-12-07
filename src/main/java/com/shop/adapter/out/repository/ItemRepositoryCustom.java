package com.shop.adapter.out.repository;


import com.shop.adapter.out.persistence.Item;
import com.shop.adapter.out.repository.dto.ItemSearchDto;
import com.shop.adapter.out.repository.dto.MainItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}