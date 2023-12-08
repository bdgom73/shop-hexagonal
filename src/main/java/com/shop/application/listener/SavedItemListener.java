package com.shop.application.listener;

import com.shop.application.event.SavedItemEvent;
import com.shop.application.port.out.item.SaveItemImagePort;
import com.shop.application.service.FileService;
import com.shop.domain.ItemImage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SavedItemListener {

    private final Logger log = LoggerFactory.getLogger(SavedItemListener.class);

    @Value("${itemImgLocation}")
    private String itemImageLocation;

    private final FileService fileService;

    private final SaveItemImagePort saveItemImagePort;

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(SavedItemEvent event) throws Exception {
        log.info("상품 등록 완료시 이벤트 발생");


        log.info("상품 이미지 등록");
        List<ItemImage> itemImages = new ArrayList<>();

        boolean isFirst = true;
        for (MultipartFile file : event.files()) {
            if (file.isEmpty()) {
                continue;
            }

            String originalFileName = file.getOriginalFilename();
            String imageName = fileService.uploadFile(itemImageLocation, originalFileName, file.getBytes());
            itemImages.add(
                    ItemImage.builder()
                            .imageName(imageName)
                            .originalFileName(originalFileName)
                            .isRepresentative(isFirst)
                            .imageUrl("/images/item/" + imageName)
                            .item(event.item())
                            .build()
            );

            isFirst = false;
        }

        saveItemImagePort.saveItemImage(itemImages);
    }
}
