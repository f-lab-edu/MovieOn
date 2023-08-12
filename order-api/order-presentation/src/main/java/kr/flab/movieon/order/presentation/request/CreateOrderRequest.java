package kr.flab.movieon.order.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import kr.flab.movieon.order.application.command.CreateOrderCommand;
import kr.flab.movieon.order.application.command.CreateOrderCommand.CreateOrderItemOptionCommand;
import kr.flab.movieon.order.application.command.CreateOrderCommand.CreateOrderLineItemCommand;

@Schema(description = "주문 생성 요청")
public final class CreateOrderRequest {

    @Schema(description = "결제 방법", example = "TOSS", required = true)
    @NotBlank(message = "주문 요청 이후 결제할 결제 방법이 필요합니다")
    private String payMethod;

    @Schema(description = "사용 포인트", example = "0", required = true)
    @NotNull(message = "포인트 사용 금액은 비어있을 수 없습니다")
    @Min(value = 0, message = "포인트 최소 사용 금액은 0원 이상이어야만 합니다")
    private Long useOfPoint;

    @Schema(description = "주문 항목", required = true)
    @NotNull(message = "주문 항목은 비어있을 수 없습니다")
    @Size(min = 1, message = "주문 항목은 반드시 하나 이상이어야만 합니다")
    @Valid
    private List<CreateOrderLineItemRequest> lineItems;

    public CreateOrderCommand toCommand() {
        return new CreateOrderCommand(this.payMethod, BigDecimal.valueOf(this.useOfPoint),
                toLineItems());
    }

    private List<CreateOrderLineItemCommand> toLineItems() {
        return this.lineItems.stream()
                .map(lineItemRequest -> new CreateOrderLineItemCommand(lineItemRequest.getItemId(),
                        lineItemRequest.getProductName(),
                        BigDecimal.valueOf(lineItemRequest.getBasePrice()),
                        toItemOptions(lineItemRequest)))
                .collect(Collectors.toList());
    }

    private List<CreateOrderItemOptionCommand> toItemOptions(
            CreateOrderLineItemRequest lineItemRequest) {
        return lineItemRequest.getOptions().stream()
                .map(itemOptionRequest -> new CreateOrderItemOptionCommand(
                        itemOptionRequest.getOptionName(),
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
    public String toString() {
        return "CreateOrderRequest{" + "payMethod='" + payMethod + '\'' + ", useOfPoint="
                + useOfPoint + ", products=" + lineItems + '}';
    }
}
