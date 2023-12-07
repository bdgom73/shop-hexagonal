package com.shop.application.service;

import com.shop.application.dto.request.ItemFormRequest;
import com.shop.application.port.in.item.SubmitITemUseCase;
import com.shop.application.port.out.item.CommandItemPort;
import com.shop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService
        implements SubmitITemUseCase {

    private final CommandItemPort commandItemPort;

    @Override
    @Transactional
    public Long save(ItemFormRequest request, List<MultipartFile> files) {
        Item item = request.toDomain();
        return commandItemPort.save(item).getId();
    }
}
