package com.shop.application.service;

import com.shop.adapter.in.web.dto.ItemFormDto;
import com.shop.adapter.out.persistence.repository.dto.MainItemDto;
import com.shop.application.dto.HomeItemDto;
import com.shop.application.dto.ItemImgResponse;
import com.shop.application.dto.ItemResponse;
import com.shop.application.dto.request.ItemSearchRequest;
import com.shop.application.event.SavedItemEvent;
import com.shop.application.port.in.item.GetItemUseCase;
import com.shop.application.port.in.item.SaveItemCommand;
import com.shop.application.port.in.item.SaveItemUseCase;
import com.shop.application.port.out.item.CommandItemPort;
import com.shop.application.port.out.item.LoadItemImagePort;
import com.shop.application.port.out.item.LoadItemPort;
import com.shop.domain.Item;
import com.shop.domain.ItemImage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    private final LoadItemImagePort loadItemImagePort;

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
    @Transactional
    public Long update(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) {
        //상품 수정
        Item item = loadItemPort.loadById(itemFormDto.id())
                .orElseThrow(EntityNotFoundException::new);
        commandItemPort.updateItem(item);
        List<Long> itemImgIds = itemFormDto.itemImgIds();
        // TODO 상품 이미지 내용 업데이트
        return item.getId();
    }

    @Override
    public Page<HomeItemDto> getMainItemPage(ItemSearchRequest request, Pageable pageable) {
        Page<MainItemDto> pageItems = loadItemPort.loadMainItem(request.toDto(), pageable);
        List<HomeItemDto> content = pageItems.getContent().stream().map(HomeItemDto::new).toList();
        return new PageImpl<>(content, pageItems.getPageable(), pageItems.getTotalElements());
    }

    @Override
    public ItemResponse getItemDtl(Long itemId){
        List<ItemImage> itemImages = loadItemImagePort.loadAllByItemIdOrderByIdAsc(itemId);

        List<ItemImgResponse> itemImgDtoList = new ArrayList<>();
        for (ItemImage itemImg : itemImages) {
            ItemImgResponse itemImgDto = ItemImgResponse.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = loadItemPort.loadById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        ItemResponse itemResponse = ItemResponse.of(item);
        itemResponse.setItemImgDtoList(itemImgDtoList);

        return itemResponse;
    }

    @Override
    public Page<ItemResponse> getAdminItem(ItemSearchRequest request, Pageable pageable) {
        Page<Item> pageItems = loadItemPort.loadAdminItem(request.toDto(), pageable);
        List<ItemResponse> content = pageItems.getContent().stream().map(ItemResponse::of).toList();
        return new PageImpl<>(content, pageItems.getPageable(), pageItems.getTotalElements());
    }
}
