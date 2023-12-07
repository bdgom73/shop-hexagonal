package com.shop.application.port.in.item;

import com.shop.application.dto.request.ItemFormRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubmitITemUseCase {
    Long save(ItemFormRequest request, List<MultipartFile> files);
}
