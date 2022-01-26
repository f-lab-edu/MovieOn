package kr.flab.movieon.order.presentation;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.flab.movieon.common.AuthenticatedUser;
import kr.flab.movieon.order.application.OrderCommandService;
import kr.flab.movieon.order.application.OrderCommandService.CreateOrderCommand;
import kr.flab.movieon.order.application.OrderCommandService.CreateOrderCommand.CreateOrderProduct;
import kr.flab.movieon.order.application.OrderCommandService.OrderInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/order",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public final class CreateOrderApi {

    private final OrderCommandService commandService;

    public CreateOrderApi(OrderCommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<OrderInfo> create(@RequestBody @Valid CreateOrderRequest request,
        @AuthenticationPrincipal AuthenticatedUser user) {
        var info = commandService.create(user.getId(), request.toCommand());
        return ResponseEntity.ok(info);
    }

    @Data
    @NoArgsConstructor
    public static final class CreateOrderRequest {

        @NotBlank(message = "주문 요청 이후 결제할 결제 방법이 필요합니다")
        private String payMethod;
        @NotNull(message = "포인트 사용 금액은 비어있을 수 없습니다")
        @Min(value = 0, message = "포인트 최소 사용 금액은 0원 이상이어야만 합니다")
        private Long useOfPoint;
        @NotNull(message = "주문 항목은 비어있을 수 없습니다")
        @Size(min = 1, message = "주문 항목은 반드시 하나 이상이어야만 합니다")
        @Valid
        private List<CreateOrderProductRequest> products;

        public CreateOrderRequest(String payMethod, Long useOfPoint,
            List<CreateOrderProductRequest> products) {
            this.payMethod = payMethod;
            this.useOfPoint = useOfPoint;
            this.products = products;
        }

        public CreateOrderCommand toCommand() {
            return CreateOrderCommand.builder()
                .payMethod(this.payMethod)
                .useOfPoint(BigDecimal.valueOf(this.useOfPoint))
                .products(this.products.stream()
                    .map(p -> new CreateOrderProduct(p.productId, p.productName,
                        BigDecimal.valueOf(p.price)))
                    .collect(Collectors.toList()))
                .build();
        }

    }

    @Data
    @NoArgsConstructor
    public static final class CreateOrderProductRequest {

        @NotNull(message = "주문 요청 상품 ID는 반드시 필요합니다")
        private Long productId;
        @NotBlank(message = "주문 요청 상품명은 반드시 필요합니다")
        private String productName;
        @NotNull(message = "주문 요청 상품 금액은 비어있을 수 없습니다")
        @Min(value = 0, message = "주문 요청 상품 최소 가격은 0원 이상이어야만 합니다")
        private Long price;

        public CreateOrderProductRequest(Long productId, String productName, Long price) {
            this.productId = productId;
            this.productName = productName;
            this.price = price;
        }
    }
}
