package kr.flab.movieon.payment.presentation;

import kr.flab.movieon.payment.application.PaymentCommandExecutor;
import kr.flab.movieon.payment.presentation.request.TossPaymentsPaymentApprovalRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentApi implements TossPaymentSpecification {

    private final PaymentCommandExecutor paymentCommandExecutor;

    public PaymentApi(PaymentCommandExecutor paymentCommandExecutor) {
        this.paymentCommandExecutor = paymentCommandExecutor;
    }

    @Override
    public void pay(TossPaymentsPaymentApprovalRequest request) {
        paymentCommandExecutor.pay(request.toCommand());
    }
}
