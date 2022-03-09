package kr.flab.movieon.order.presentation;

import javax.validation.Valid;
import kr.flab.movieon.common.AuthenticatedUser;
import kr.flab.movieon.common.result.ApiResponse;
import kr.flab.movieon.order.application.OrderFacade;
import kr.flab.movieon.order.application.request.CreateOrderRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/orders",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public final class CreateOrderApi {

    private final OrderFacade orderFacade;

    public CreateOrderApi(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(
        @RequestBody @Valid CreateOrderRequest request,
        @AuthenticationPrincipal AuthenticatedUser user) {
        var info = orderFacade.create(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.success(info));
    }
}
