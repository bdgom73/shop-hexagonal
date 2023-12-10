package com.shop.application.service;

import com.shop.application.aop.RedissonLock;
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
    @RedissonLock(key = "orders")
    public Long order(String email, ItemRequest request) {
        Member member = loadMemberPort.loadByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        List<OrderItem> orderItems = addOrderItems(List.of(request));
        Order order = Order.createOrder(member, orderItems);
        return commandOrderPort.save(order).getId();
    }

    @Override
    @Transactional
    @RedissonLock(key = "orders")
    public void orders(String email, List<ItemRequest> requests) {
        Member member = loadMemberPort.loadByEmail(email)
                .orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItemList = addOrderItems(requests);
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
    @RedissonLock(key = "orders")
    public void cancelOrder(Long orderId){
        commandOrderPort.cancel(orderId);
    }

    /**
     * 주문 상품 추가
     * @param requests 상품 정보 ( 상품 식별자, 구매 수량 이 포함된 ) 객체
     * @return List<OrderItem> 주문 상품 ( 원본 상품 도메인 이 포함된 ) 목록 반환
     * @throws EntityNotFoundException 원본 상품이 존재 하지 않는 경우 발생 하는 예외
     * */
    private List<OrderItem> addOrderItems(List<ItemRequest> requests) {
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

            // 상품 수량 변경
            updateStock(item);

            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    /**
     * 재고 수량 변경
     * @param item 상품 도메인 객체 ( 이미 수량 변경이 적용된 Item 객체 )
     * */
    private void updateStock(Item item) {
        commandItemPort.updateStock(item);
    }
}
