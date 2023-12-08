package com.shop.adapter.in.web;

import com.shop.adapter.in.web.dto.ItemFormDto;
import com.shop.application.port.in.item.SaveItemCommand;
import com.shop.application.port.in.item.SaveItemUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final SaveItemUseCase saveItemUseCase;

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", ItemFormDto.empty());
        return "item/itemForm";
    }

    @PostMapping(value = "/admin/item/new")
    public String itemNew(@ModelAttribute("itemFormDto") @Valid ItemFormDto itemFormDto,
                          @RequestParam(value = "itemImgFile") List<MultipartFile> itemImgFileList,
                          BindingResult bindingResult,
                          Model model) {

        System.out.println("itemFormDto = " + itemImgFileList.size());
        for (MultipartFile multipartFile : itemImgFileList) {
            System.out.println("multipartFile = " + multipartFile.getOriginalFilename());
        }

        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.id() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try {
            saveItemUseCase.save(
                    new SaveItemCommand(
                            itemFormDto.itemNm(),
                            itemFormDto.price(),
                            itemFormDto.itemDetail(),
                            itemFormDto.stockNumber(),
                            itemImgFileList
                    )
            );
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }

}