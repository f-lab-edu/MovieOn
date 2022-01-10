package kr.flab.movieon.payment.integrate.validator;

import java.math.BigDecimal;
import kr.flab.movieon.payment.domain.Payment;
import kr.flab.movieon.payment.domain.PaymentValidator;
import kr.flab.movieon.payment.domain.exception.InvalidPaymentCommandException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public final class PaymentAmountValidator implements PaymentValidator {

    @Override
    public void validate(Payment payment) {
        if (isNegative(payment.calculateTotalAmount())) {
            throw new InvalidPaymentCommandException(
                "Total payment amount cannot be negative.");
        }
    }

    private boolean isNegative(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }
}
