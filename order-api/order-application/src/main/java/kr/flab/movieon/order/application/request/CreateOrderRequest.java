package kr.flab.movieon.order.application.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.flab.movieon.order.domain.commands.CreateOrder;
import kr.flab.movieon.order.domain.commands.CreateOrder.CreateOrderProduct;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public final class CreateOrderRequest {

    @NotBlank(message = "주문 요청 이후 결제할 결제 방법이 필요합니다")
    private String payMethod;
    @NotNull(message = "포인트 사용 금액은 비어있을 수 없습니다")
    @Min(value = 0, message = "포인트 최소 사용 금액은 0원 이상이어야만 합니다")
    private Long useOfPoint;
    @NotNull(message = "주문 항목은 비어있을 수 없습니다")
    @Size(min = 1, message = "주문 항목은 반드시 하나 이상이어야만 합니다")
    @Valid
    private List<CreateOrderProductRequest> products;

    public CreateOrder toCommand() {
        return CreateOrder.builder()
            .payMethod(this.payMethod)
            .useOfPoint(BigDecimal.valueOf(this.useOfPoint))
            .products(this.products.stream()
                .map(p -> new CreateOrderProduct(p.getProductId(), p.getProductName(),
                    BigDecimal.valueOf(p.getPrice())))
                .collect(Collectors.toList()))
            .build();
    }

}
