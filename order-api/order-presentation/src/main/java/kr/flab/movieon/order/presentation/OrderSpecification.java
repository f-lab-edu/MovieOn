package kr.flab.movieon.order.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import kr.flab.movieon.common.AuthenticatedUser;
import kr.flab.movieon.common.result.ApiResponseEnvelop;
import kr.flab.movieon.order.presentation.request.CreateOrderRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Order", description = "주문 API")
public interface OrderSpecification {

    @Operation(summary = "주문 생성", description = "주문을 생성하기 위한 Endpoint를 제공합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "주문 생성 성공"),
        @ApiResponse(responseCode = "400", description = "주문 생성 실패 에러", content = @Content)
    })
    @PostMapping(value = "/api/v1/orders",
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiResponseEnvelop<URI>> create(@RequestBody @Valid CreateOrderRequest request,
        @AuthenticationPrincipal AuthenticatedUser user);
}
