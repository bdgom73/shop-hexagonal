package com.shop.adapter.in.web.dto.command;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderCommand {

    private Long cartItemId;

    private List<CartOrderCommand> cartOrderDtoList;

}