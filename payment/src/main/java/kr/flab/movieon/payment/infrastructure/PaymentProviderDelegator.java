package kr.flab.movieon.payment.infrastructure;

import java.util.HashMap;
import java.util.Map;
import kr.flab.movieon.payment.domain.Payment;
import kr.flab.movieon.payment.domain.PaymentProvider;
import kr.flab.movieon.payment.domain.PaymentType;

public final class PaymentProviderDelegator implements PaymentProvider {

    private static final Map<PaymentType, PaymentProvider> providerMap
        = new HashMap<>();

    static {
        providerMap.put(PaymentType.TOSS, new TossPaymentsPaymentProvider());
        providerMap.put(PaymentType.KAKAO_PAY, new KakaoPayPaymentProvider());
    }

    @Override
    public void payed(Payment payment) {
        providerMap.get(payment.getType()).payed(payment);
    }
}
