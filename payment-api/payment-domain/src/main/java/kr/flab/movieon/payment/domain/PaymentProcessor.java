package kr.flab.movieon.payment.domain;

import java.util.List;
import kr.flab.movieon.payment.domain.Payment.Type;
import kr.flab.movieon.payment.domain.exception.InvalidPaymentCommandException;
import org.springframework.stereotype.Component;

@Component
public final class PaymentProcessor {

    private final List<PaymentApiProvider> paymentApiProviderList;
    private final PaymentRepository paymentRepository;

    public PaymentProcessor(List<PaymentApiProvider> paymentApiProviderList,
        PaymentRepository paymentRepository) {
        this.paymentApiProviderList = paymentApiProviderList;
        this.paymentRepository = paymentRepository;
    }

    public Payment pay(PaymentApprovalCommand command) {
        var paymentApiProvider = routingPaymentApiProvider(command.getPaymentType());

        var payment = paymentApiProvider.pay(command);
        return paymentRepository.save(payment);
    }

    private PaymentApiProvider routingPaymentApiProvider(Type type) {
        return paymentApiProviderList.stream()
            .filter(paymentApiProvider -> paymentApiProvider.support(type))
            .findFirst().orElseThrow(
                () -> new InvalidPaymentCommandException("This payment method is not supported."));
    }
}
