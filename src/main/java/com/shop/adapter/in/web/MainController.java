package com.shop.adapter.in.web;

import com.shop.adapter.in.web.dto.command.ItemCommand;
import com.shop.adapter.in.web.dto.command.ItemSearchCommand;
import com.shop.application.dto.HomeItemDto;
import com.shop.application.dto.request.ItemRequest;
import com.shop.application.port.in.item.GetItemUseCase;
import com.shop.application.port.in.order.DoOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final DoOrderUseCase doOrderUseCase;
    private final GetItemUseCase getItemUseCase;

    @GetMapping(value = "/")
    public String main(ItemSearchCommand itemSearchCommand, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.orElse(0), 6);
        Page<HomeItemDto> items = getItemUseCase.getMainItemPage(itemSearchCommand.toRequest(), pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchCommand);
        model.addAttribute("maxPage", 5);

        return "main";
    }

    @GetMapping("/test/order")
    @ResponseBody
    public void order(
            @RequestBody ItemCommand itemCommand
            ) {
        doOrderUseCase.order("bdgom73@naver.com", itemCommand.toRequest());
    }

    @GetMapping("/test/order2")
    @ResponseBody
    public void order(
            @RequestBody List<ItemCommand> itemCommand
    ) {
        List<ItemRequest> list = itemCommand.stream()
                .map(ItemCommand::toRequest).toList();
        doOrderUseCase.orders("bdgom73@naver.com", list);
    }
}