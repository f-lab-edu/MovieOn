package kr.flab.movieon.query.modules.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.flab.movieon.common.result.ApiResponseEnvelop;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "OrderQuery", description = "주문 조회 API")
public interface OrderQuerySpecification {

    @Operation(summary = "주문 조회", description = "주문 정보를 조회하기 위한 Endpoint를 제공합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "주문 조회 성공"),
        @ApiResponse(responseCode = "404", description = "주문이 존재하지 않는 에러",
            content = @Content)
    })
    @GetMapping(value = "/api/v1/orders/{orderId}",
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ApiResponseEnvelop<OrderReadModel>> findOrderInfo(@PathVariable String orderId);
}
