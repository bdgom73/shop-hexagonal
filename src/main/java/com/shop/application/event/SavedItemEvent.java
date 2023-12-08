package com.shop.application.event;


import com.shop.domain.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record SavedItemEvent(Item item, List<MultipartFile> files) {
}
