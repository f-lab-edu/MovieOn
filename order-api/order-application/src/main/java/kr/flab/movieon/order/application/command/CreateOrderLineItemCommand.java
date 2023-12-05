package kr.flab.movieon.order.application.command;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderLineItemCommand(Long itemId, String productName, BigDecimal basePrice,
                                         List<CreateOrderItemOptionCommand> options) {
}
