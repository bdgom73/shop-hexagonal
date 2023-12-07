package com.shop.application.port.in.order;

import com.shop.application.dto.request.ItemRequest;

public interface DoOrderUseCase {
    void order(String email, ItemRequest request);
}
