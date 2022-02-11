package kr.flab.movieon.order.application.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public final class CreateOrderProductRequest {

    @NotNull(message = "주문 요청 상품 ID는 반드시 필요합니다")
    private Long productId;
    @NotBlank(message = "주문 요청 상품명은 반드시 필요합니다")
    private String productName;
    @NotNull(message = "주문 요청 상품 금액은 비어있을 수 없습니다")
    @Min(value = 0, message = "주문 요청 상품 최소 가격은 0원 이상이어야만 합니다")
    private Long price;
}
