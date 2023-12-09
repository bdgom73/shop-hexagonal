package com.shop.application.event;

import com.shop.application.dto.OrderDto;

import java.util.List;

public record OrderEvent(String email, List<OrderDto> orderDtoList) {
}
