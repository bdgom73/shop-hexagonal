package com.shop.adapter.in.web;

import com.shop.adapter.in.web.dto.ItemFormDto;
import com.shop.adapter.in.web.dto.command.ItemSearchCommand;
import com.shop.application.dto.ItemResponse;
import com.shop.application.port.in.item.GetItemUseCase;
import com.shop.application.port.in.item.SaveItemCommand;
import com.shop.application.port.in.item.SaveItemUseCase;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final SaveItemUseCase saveItemUseCase;
    private final GetItemUseCase getItemUseCase;

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
                            itemFormDto.itemSellStatus(),
                            itemImgFileList
                    )
            );
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }

    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model,
                             @PathVariable Long itemId){
        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.id() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try {
            saveItemUseCase.update(itemFormDto, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }


    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){

        try {
            ItemResponse itemDtl = getItemUseCase.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemDtl);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("itemFormDto", new ItemResponse());
            return "item/itemForm";
        }

        return "item/itemForm";
    }

    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){
        ItemResponse itemDtl = getItemUseCase.getItemDtl(itemId);
        model.addAttribute("item", itemDtl);
        return "item/itemDtl";
    }

    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchCommand itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.orElse(0), 3);
        Page<ItemResponse> items = getItemUseCase.getAdminItem(itemSearchDto.toRequest(), pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "item/itemMng";
    }



}