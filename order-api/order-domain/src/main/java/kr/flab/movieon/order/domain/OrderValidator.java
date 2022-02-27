package kr.flab.movieon.order.domain;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Map;

public class OrderValidator {

    private final ItemRepository itemRepository;
    private final PointManager pointManager;

    public OrderValidator(ItemRepository itemRepository, PointManager pointManager) {
        this.itemRepository = itemRepository;
        this.pointManager = pointManager;
    }

    public void validate(Order order) {
        if (order.getLineItems() == null || order.getLineItems().isEmpty()) {
            throw new IllegalStateException("주문 항목이 비어 있습니다.");
        }

        var items = getItems(order);

        for (OrderLineItem orderLineItem : order.getLineItems()) {
            validateOrderLineItem(orderLineItem, items.get(orderLineItem.getItemId()));
        }

        pointManager.canUseOfPoint(order.getCustomer().getAccountId(), order.getUseOfPoint());
    }

    private void validateOrderLineItem(OrderLineItem orderLineItem, Item item) {
        if (!item.isSatisfiedBy(orderLineItem.getName(), orderLineItem.getBasePrice())) {
            throw new IllegalStateException("기본 상품 정보가 변경되었습니다.");
        }
        if (!item.isSatisfiedBy(orderLineItem.getOptions())) {
            throw new IllegalStateException("상품 옵션 정보가 변경되었습니다.");
        }
    }

    private Map<Long, Item> getItems(Order order) {
        return itemRepository.findAllById(order.getItemIds())
            .stream()
            .collect(toMap(Item::getId, identity()));
    }
}
