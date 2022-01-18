package kr.flab.movieon.payment.presentation;

import javax.validation.Valid;
import kr.flab.movieon.common.result.ApiResponse;
import kr.flab.movieon.payment.application.PaymentApprovalRequest;
import kr.flab.movieon.payment.application.PaymentFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/payments",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public final class PaymentController {

    private final PaymentFacade paymentFacade;

    public PaymentController(PaymentFacade paymentFacade) {
        this.paymentFacade = paymentFacade;
    }

    @PostMapping("/approve")
    public ApiResponse<?> approvePayment(
        @Valid @RequestBody PaymentApprovalRequest request) {
        paymentFacade.approve(request);

        return ApiResponse.success();
    }
}
