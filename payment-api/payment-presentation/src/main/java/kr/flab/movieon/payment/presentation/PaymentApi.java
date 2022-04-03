package kr.flab.movieon.payment.presentation;

import kr.flab.movieon.payment.application.PaymentFacade;
import kr.flab.movieon.payment.presentation.request.TossPaymentsPaymentApprovalRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentApi implements TossPaymentSpecification {

    private final PaymentFacade paymentFacade;

    public PaymentApi(PaymentFacade paymentFacade) {
        this.paymentFacade = paymentFacade;
    }

    @Override
    public void payed(TossPaymentsPaymentApprovalRequest request) {
        paymentFacade.payed(request.toCommand());
    }
}
