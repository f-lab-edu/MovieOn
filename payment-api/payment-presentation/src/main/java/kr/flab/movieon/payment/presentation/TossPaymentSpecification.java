package kr.flab.movieon.payment.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import kr.flab.movieon.payment.presentation.request.TossPaymentsPaymentApprovalRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "TossPayment", description = "토스 페이먼츠 API")
public interface TossPaymentSpecification {

    @Operation(summary = "토스 페이먼츠 결제 완료", description = "토스 페이먼츠 PG 결제 "
        + "완료 처리를 위한 EndPoint를 제공합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "결제 처리 성공"),
        @ApiResponse(responseCode = "400", description = "결제 처리 에러", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/api/v1/toss-payments/success")
    void payed(@RequestBody @Valid TossPaymentsPaymentApprovalRequest request);
}
