package com.shop.application.port.in.order;

public interface ValidateOrderUseCase {

    boolean validateOrder(Long orderId, String email);
}
