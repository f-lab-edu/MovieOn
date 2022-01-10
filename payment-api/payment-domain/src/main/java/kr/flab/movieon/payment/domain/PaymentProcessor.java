package kr.flab.movieon.payment.domain;

import java.util.List;
import kr.flab.movieon.payment.domain.Payment.Type;
import kr.flab.movieon.payment.domain.exception.InvalidPaymentCommandException;
import org.springframework.stereotype.Component;

@Component
public final class PaymentProcessor {

    private final List<PaymentProvider> paymentProviderList;
    private final PaymentRepository paymentRepository;

    public PaymentProcessor(List<PaymentProvider> paymentProviderList,
        PaymentRepository paymentRepository) {
        this.paymentProviderList = paymentProviderList;
        this.paymentRepository = paymentRepository;
    }

    public void pay(PaymentApprovalCommand command, String pgToken) {
        var paymentProvider = routingPaymentProvider(command.getPaymentType());

        if (!paymentProvider.validate(command, pgToken)) {
            throw new InvalidPaymentCommandException();
        }

        var payment = paymentProvider.pay(command, pgToken);
        paymentRepository.save(payment);

    }

    private PaymentProvider routingPaymentProvider(Type type) {
        return paymentProviderList.stream().filter(paymentProvider -> paymentProvider.support(type))
            .findFirst().orElseThrow(
                () -> new InvalidPaymentCommandException("This payment method is not supported."));
    }
}
