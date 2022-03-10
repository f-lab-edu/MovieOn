package kr.flab.movieon.payment.presentation;

import javax.validation.Valid;
import kr.flab.movieon.payment.application.PaymentFacade;
import kr.flab.movieon.payment.application.TossPaymentsPaymentApprovalRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PaymentApi {

    private final PaymentFacade paymentFacade;

    public PaymentApi(PaymentFacade paymentFacade) {
        this.paymentFacade = paymentFacade;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/toss-payments/success")
    public void payed(@RequestBody @Valid TossPaymentsPaymentApprovalRequest request) {
        paymentFacade.payed(request);
    }
}
