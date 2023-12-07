package com.shop.adapter.in.web;

import com.shop.adapter.in.web.dto.command.ItemFormCommand;
import com.shop.application.port.in.item.SubmitITemUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final SubmitITemUseCase submitITemUseCase;

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto", new ItemFormCommand());
        return "item/itemForm";
    }

    @PostMapping(value = "/admin/item/new")
    public String itemNew(@ModelAttribute("itemFormDto") @Valid ItemFormCommand itemFormDto, BindingResult bindingResult,
                          Model model
//                          @RequestParam(value = "itemImgFile", required = false) List<MultipartFile> itemImgFileList
    ){

        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

//        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
//            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
//            return "item/itemForm";
//        }

        try {
            submitITemUseCase.save(itemFormDto.toRequest(), new ArrayList<>());
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }

}