package com.shop.application.service;

import com.shop.application.event.SavedItemEvent;
import com.shop.application.port.in.item.SaveItemCommand;
import com.shop.application.port.in.item.SaveItemUseCase;
import com.shop.application.port.out.item.CommandItemPort;
import com.shop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService implements SaveItemUseCase {

    private final CommandItemPort commandItemPort;
    private final ApplicationEventPublisher publisher;

    @Override
    public Long save(SaveItemCommand saveItemCommand) {

        // 상품 등록
        Item item = saveItemCommand.toDomain();
        Long id = commandItemPort.save(item).getId();

        // 이미지 등록
        publisher.publishEvent(new SavedItemEvent(item, saveItemCommand.files()));

        return id;
    }
}
