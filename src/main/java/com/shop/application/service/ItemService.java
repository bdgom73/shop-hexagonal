package com.shop.application.service;

import com.shop.adapter.out.persistence.repository.dto.MainItemDto;
import com.shop.application.dto.HomeItemDto;
import com.shop.application.dto.request.ItemSearchRequest;
import com.shop.application.event.SavedItemEvent;
import com.shop.application.port.in.item.GetItemUseCase;
import com.shop.application.port.in.item.SaveItemCommand;
import com.shop.application.port.in.item.SaveItemUseCase;
import com.shop.application.port.out.item.CommandItemPort;
import com.shop.application.port.out.item.LoadItemPort;
import com.shop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService implements
        SaveItemUseCase,
        GetItemUseCase
{

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final CommandItemPort commandItemPort;
    private final ApplicationEventPublisher publisher;
    private final LoadItemPort loadItemPort;

    @Override
    @Transactional
    public Long save(SaveItemCommand saveItemCommand) {

        // 상품 등록
        Item item = saveItemCommand.toDomain();
        Item saveItem = commandItemPort.save(item);

        // 이미지 등록
        publisher.publishEvent(new SavedItemEvent(saveItem, saveItemCommand.files()));

        return saveItem.getId();
    }

    @Override
    public Page<HomeItemDto> getMainItemPage(ItemSearchRequest request, Pageable pageable) {
        Page<MainItemDto> pageItems = loadItemPort.loadMainItem(request.toDto(), pageable);
        List<HomeItemDto> content = pageItems.getContent().stream().map(HomeItemDto::new).toList();
        return new PageImpl<>(content, pageItems.getPageable(), pageItems.getTotalElements());
    }
}
