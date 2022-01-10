package kr.flab.movieon.payment.integrate;

import java.util.List;
import kr.flab.movieon.payment.domain.Payment;
import kr.flab.movieon.payment.domain.PaymentMethod;
import kr.flab.movieon.payment.domain.PaymentProcessor;
import kr.flab.movieon.payment.domain.PaymentProvider;
import kr.flab.movieon.payment.domain.PaymentRepository;
import kr.flab.movieon.payment.domain.PaymentValidator;
import kr.flab.movieon.payment.domain.exception.InvalidPaymentCommandException;
import kr.flab.movieon.purchase.domain.PurchaseRepository;
import org.springframework.stereotype.Component;

@Component
public final class PurchasePaymentProcessor implements PaymentProcessor {

    private final PurchaseRepository purchaseRepository;
    private final PaymentRepository paymentRepository;
    private final List<PaymentValidator> paymentValidatorList;
    private final List<PaymentProvider> paymentProviderList;

    public PurchasePaymentProcessor(
        PurchaseRepository purchaseRepository,
        PaymentRepository paymentRepository,
        List<PaymentValidator> paymentValidatorList,
        List<PaymentProvider> paymentProviderList) {
        this.purchaseRepository = purchaseRepository;
        this.paymentRepository = paymentRepository;
        this.paymentValidatorList = paymentValidatorList;
        this.paymentProviderList = paymentProviderList;
    }

    @Override
    public void pending(Long purchaseId, String paymentMethod) {
        var purchase = purchaseRepository.findById(purchaseId);

        var payment = paymentRepository.save(
            Payment.create(
                purchase.getId(),
                purchase.getTitle(),
                purchase.getPurchaserId(),
                purchase.getPrice(),
                PaymentMethod.valueOf(paymentMethod)
            ));

        var paymentProvider = routingPaymentProvider(payment);
        paymentProvider.pending(payment);
    }

    @Override
    public void pay(Long purchaseId, String purchaseToken) {
        var payment = paymentRepository.findByPurchaseId(purchaseId)
            .orElseThrow(() -> new InvalidPaymentCommandException(
                "The payment cannot be completed, This payment is an invalid."));

        paymentValidatorList.forEach(
            paymentValidator -> paymentValidator.validate(payment));

        var paymentProvider = routingPaymentProvider(payment);
        paymentProvider.pay(payment, purchaseToken);
    }

    private PaymentProvider routingPaymentProvider(Payment payment) {
        return paymentProviderList.stream()
            .filter(paymentProvider -> paymentProvider.support(payment.getPaymentMethod()))
            .findFirst()
            .orElseThrow(
                () -> new InvalidPaymentCommandException("This payment method is not supported."));
    }
}
