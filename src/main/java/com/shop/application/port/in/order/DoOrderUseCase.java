package com.shop.application.port.in.order;

import com.shop.adapter.in.web.dto.input.ItemInput;

public interface DoOrderUseCase {
    void order(String email, ItemInput input);
}
