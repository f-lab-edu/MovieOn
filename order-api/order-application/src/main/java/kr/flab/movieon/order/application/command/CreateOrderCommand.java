package kr.flab.movieon.order.application.command;

import java.math.BigDecimal;
import java.util.List;

public final class CreateOrderCommand {

    private final String payMethod;
    private final BigDecimal useOfPoint;
    private final List<CreateOrderLineItemCommand> lineItems;

    public CreateOrderCommand(String payMethod, BigDecimal useOfPoint,
        List<CreateOrderLineItemCommand> lineItems) {
        this.payMethod = payMethod;
        this.useOfPoint = useOfPoint;
        this.lineItems = lineItems;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public BigDecimal getUseOfPoint() {
        return useOfPoint;
    }

    public List<CreateOrderLineItemCommand> getLineItems() {
        return lineItems;
    }

    public static final class CreateOrderLineItemCommand {

        private final Long itemId;
        private final String productName;
        private final BigDecimal basePrice;
        private final List<CreateOrderItemOptionCommand> options;

        public CreateOrderLineItemCommand(Long itemId, String productName, BigDecimal basePrice,
            List<CreateOrderItemOptionCommand> options) {
            this.itemId = itemId;
            this.productName = productName;
            this.basePrice = basePrice;
            this.options = options;
        }

        public Long getItemId() {
            return itemId;
        }

        public String getProductName() {
            return productName;
        }

        public BigDecimal getBasePrice() {
            return basePrice;
        }

        public List<CreateOrderItemOptionCommand> getOptions() {
            return options;
        }
    }

    public static final class CreateOrderItemOptionCommand {

        private final String optionName;
        private final BigDecimal salesPrice;

        public CreateOrderItemOptionCommand(String optionName, BigDecimal salesPrice) {
            this.optionName = optionName;
            this.salesPrice = salesPrice;
        }

        public String getOptionName() {
            return optionName;
        }

        public BigDecimal getSalesPrice() {
            return salesPrice;
        }
    }
}
