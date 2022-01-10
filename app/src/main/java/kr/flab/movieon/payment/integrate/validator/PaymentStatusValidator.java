package kr.flab.movieon.payment.integrate.validator;

import kr.flab.movieon.payment.domain.Payment;
import kr.flab.movieon.payment.domain.PaymentValidator;
import kr.flab.movieon.payment.domain.exception.InvalidPaymentCommandException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public final class PaymentStatusValidator implements PaymentValidator {

    @Override
    public void validate(Payment payment) {
        if (!payment.isPaymentPending()) {
            throw new InvalidPaymentCommandException("The payment has already been processed.");
        }
    }
}

