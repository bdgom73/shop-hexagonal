package com.shop.application.port.in.order;

import com.shop.application.dto.request.ItemRequest;

import java.util.List;

public interface DoOrderUseCase {
    Long order(String email, ItemRequest request);
    void orders(String email, List<ItemRequest> requests);
    void cancelOrder(Long orderId);
}
