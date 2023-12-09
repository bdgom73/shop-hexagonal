package com.shop.application.port.in.item;

import com.shop.adapter.in.web.dto.ItemFormDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SaveItemUseCase {
    Long save(SaveItemCommand saveItemCommand);
    Long update(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList);
}
