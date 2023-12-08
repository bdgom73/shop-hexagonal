package com.shop.application.service;

import com.shop.adapter.out.persistence.repository.dto.MainItemDto;
import com.shop.application.dto.HomeItemDto;
import com.shop.application.dto.request.ItemFormRequest;
import com.shop.application.dto.request.ItemSearchRequest;
import com.shop.application.port.in.item.GetItemUseCase;
import com.shop.application.port.in.item.SubmitITemUseCase;
import com.shop.application.port.out.item.CommandItemPort;
import com.shop.application.port.out.item.LoadItemPort;
import com.shop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService
        implements SubmitITemUseCase,
        GetItemUseCase {

    private final CommandItemPort commandItemPort;
    private final LoadItemPort loadItemPort;

    @Override
    @Transactional
    public Long save(ItemFormRequest request, List<MultipartFile> files) {
        Item item = request.toDomain();
        return commandItemPort.save(item).getId();
    }

    @Override
    public Page<HomeItemDto> getMainItemPage(ItemSearchRequest request, Pageable pageable) {
        Page<MainItemDto> pageItems = loadItemPort.loadMainItem(request.toDto(), pageable);
        List<HomeItemDto> content = pageItems.getContent().stream().map(HomeItemDto::new).toList();
        return new PageImpl<>(content, pageItems.getPageable(), pageItems.getTotalElements());
    }
}
