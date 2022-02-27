package kr.flab.movieon.order.application.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.flab.movieon.order.domain.commands.CreateOrder;
import kr.flab.movieon.order.domain.commands.CreateOrder.CreateOrderItemOption;
import kr.flab.movieon.order.domain.commands.CreateOrder.CreateOrderLineItem;

public final class CreateOrderRequest {

    @NotBlank(message = "주문 요청 이후 결제할 결제 방법이 필요합니다")
    private String payMethod;
    @NotNull(message = "포인트 사용 금액은 비어있을 수 없습니다")
    @Min(value = 0, message = "포인트 최소 사용 금액은 0원 이상이어야만 합니다")
    private Long useOfPoint;
    @NotNull(message = "주문 항목은 비어있을 수 없습니다")
    @Size(min = 1, message = "주문 항목은 반드시 하나 이상이어야만 합니다")
    @Valid
    private List<CreateOrderLineItemRequest> lineItems;

    public CreateOrder toCommand() {
        return new CreateOrder(this.payMethod, BigDecimal.valueOf(this.useOfPoint), toLineItems());
    }

    private List<CreateOrderLineItem> toLineItems() {
        return this.lineItems.stream()
            .map(lineItemRequest -> new CreateOrderLineItem(lineItemRequest.getItemId(),
                lineItemRequest.getProductName(),
                BigDecimal.valueOf(lineItemRequest.getBasePrice()),
                toItemOptions(lineItemRequest)))
            .collect(Collectors.toList());
    }

    private List<CreateOrderItemOption> toItemOptions(CreateOrderLineItemRequest lineItemRequest) {
        return lineItemRequest.getOptions().stream()
            .map(itemOptionRequest -> new CreateOrderItemOption(itemOptionRequest.getOptionName(),
                BigDecimal.valueOf(itemOptionRequest.getSalesPrice())))
            .collect(Collectors.toList());
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Long getUseOfPoint() {
        return useOfPoint;
    }

    public void setUseOfPoint(Long useOfPoint) {
        this.useOfPoint = useOfPoint;
    }

    public List<CreateOrderLineItemRequest> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<CreateOrderLineItemRequest> lineItems) {
        this.lineItems = lineItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateOrderRequest that = (CreateOrderRequest) o;
        return Objects.equals(payMethod, that.payMethod) && Objects.equals(useOfPoint,
            that.useOfPoint) && Objects.equals(lineItems, that.lineItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payMethod, useOfPoint, lineItems);
    }

    @Override
    public String toString() {
        return "CreateOrderRequest{" + "payMethod='" + payMethod + '\'' + ", useOfPoint="
            + useOfPoint + ", products=" + lineItems + '}';
    }
}
