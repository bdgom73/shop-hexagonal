package com.shop.application.service;

import com.shop.application.dto.OrderHistDto;
import com.shop.application.dto.OrderItemDto;
import com.shop.application.dto.request.ItemRequest;
import com.shop.application.port.in.order.DoOrderUseCase;
import com.shop.application.port.in.order.GetOrderUseCase;
import com.shop.application.port.in.order.ValidateOrderUseCase;
import com.shop.application.port.out.item.CommandItemPort;
import com.shop.application.port.out.item.LoadItemPort;
import com.shop.application.port.out.member.LoadMemberPort;
import com.shop.application.port.out.order.CommandOrderPort;
import com.shop.application.port.out.order.LoadOrderPort;
import com.shop.application.port.out.order.item.LoadOrderItemPort;
import com.shop.domain.Item;
import com.shop.domain.Member;
import com.shop.domain.Order;
import com.shop.domain.OrderItem;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService
        implements DoOrderUseCase,
        GetOrderUseCase,
        ValidateOrderUseCase {

    private final LoadItemPort loadItemPort;
    private final CommandItemPort commandItemPort;
    private final LoadMemberPort loadMemberPort;
    private final CommandOrderPort commandOrderPort;
    private final LoadOrderPort loadOrderPort;
    private final LoadOrderItemPort loadOrderItemPort;

    @Override
    @Transactional
    public Long order(String email, ItemRequest request) {
        Item item = loadItemPort.loadById(request.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = loadMemberPort.loadByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        OrderItem orderItem = OrderItem.createOrderItem(item, request.getCount());
        commandItemPort.updateStock(item);
        Order order = Order.createOrder(member, Collections.singletonList(orderItem));
        return commandOrderPort.save(order).getId();
    }

    @Override
    @Transactional
    public void orders(String email, List<ItemRequest> requests) {
        Member member = loadMemberPort.loadByEmail(email)
                .orElseThrow(EntityNotFoundException::new);

        List<Long> itemIds = requests.stream().map(ItemRequest::getItemId).toList();
        Map<Long, Item> itemMap = loadItemPort.loadAllByIn(itemIds).stream()
                .collect(Collectors.toMap(
                        Item::getId,
                        _this -> _this
                ));

        List<OrderItem> orderItemList = new ArrayList<>();
        for (ItemRequest request : requests) {
            Item item = itemMap.get(request.getItemId());

            if (ObjectUtils.isEmpty(item)) {
                throw new EntityNotFoundException();
            }

            OrderItem orderItem = OrderItem.createOrderItem(item, request.getCount());
            commandItemPort.updateStock(item);
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(member, orderItemList);
        commandOrderPort.save(order);
    }

    @Override
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {
        List<Order> orders = loadOrderPort.loadByEmail(email, pageable);
        Long totalCount = loadOrderPort.loadOrderCountByEmail(email);

        List<Long> orderIds = orders.stream().map(Order::getId).toList();

        List<OrderItem> findOrderItems = loadOrderItemPort.loadAllByOrders(orderIds);

        List<OrderHistDto> orderHistDtoList = orders.stream()
                .map(order -> {
                    OrderHistDto orderHistDto = new OrderHistDto(order);

                    List<OrderItem> orderItems = findOrderItems
                            .stream()
                            .filter(orderItem -> orderItem.getOrder().getId().equals(order.getId()))
                            .toList();

                    for (OrderItem orderItem : orderItems) {
                        OrderItemDto orderItemDto =
                                new OrderItemDto(orderItem, "");
                        orderHistDto.addOrderItemDto(orderItemDto);
                    }

                    return orderHistDto;
                })
                .toList();

        return new PageImpl<>(orderHistDtoList, pageable, totalCount);
    }

    @Override
    public boolean validateOrder(Long orderId, String email) {
        Member curMember = loadMemberPort.loadByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        Order order = loadOrderPort.loadById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        Member savedMember = order.getMember();

        if (!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId){
        commandOrderPort.cancel(orderId);
    }

}
