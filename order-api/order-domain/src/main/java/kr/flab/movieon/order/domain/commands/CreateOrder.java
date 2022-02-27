package kr.flab.movieon.order.domain.commands;

import java.math.BigDecimal;
import java.util.List;

public final class CreateOrder {

    private final String payMethod;
    private final BigDecimal useOfPoint;
    private final List<CreateOrderLineItem> lineItems;

    public CreateOrder(String payMethod, BigDecimal useOfPoint,
        List<CreateOrderLineItem> lineItems) {
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

    public List<CreateOrderLineItem> getLineItems() {
        return lineItems;
    }

    public static final class CreateOrderLineItem {

        private final Long itemId;
        private final String productName;
        private final BigDecimal basePrice;
        private final List<CreateOrderItemOption> options;

        public CreateOrderLineItem(Long itemId, String productName, BigDecimal basePrice,
            List<CreateOrderItemOption> options) {
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

        public List<CreateOrderItemOption> getOptions() {
            return options;
        }
    }

    public static final class CreateOrderItemOption {

        private final String optionName;
        private final BigDecimal salesPrice;

        public CreateOrderItemOption(String optionName, BigDecimal salesPrice) {
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
