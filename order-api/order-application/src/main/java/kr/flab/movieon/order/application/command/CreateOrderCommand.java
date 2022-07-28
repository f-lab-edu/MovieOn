package kr.flab.movieon.order.application.command;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderCommand(String payMethod, BigDecimal useOfPoint,
                                 List<CreateOrderLineItemCommand> lineItems) {
    public record CreateOrderLineItemCommand(Long itemId, String productName, BigDecimal basePrice,
                                             List<CreateOrderItemOptionCommand> options) {}

    public record CreateOrderItemOptionCommand(String optionName, BigDecimal salesPrice) {}
}
