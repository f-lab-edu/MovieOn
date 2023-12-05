package kr.flab.movieon.order.application.command;

import java.math.BigDecimal;

public record CreateOrderItemOptionCommand(String optionName, BigDecimal salesPrice) {
}
