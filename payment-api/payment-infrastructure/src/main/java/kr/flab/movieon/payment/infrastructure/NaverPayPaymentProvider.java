package kr.flab.movieon.payment.infrastructure;

import kr.flab.movieon.payment.domain.Payment;
import kr.flab.movieon.payment.domain.PaymentMethod;
import kr.flab.movieon.payment.domain.PaymentProvider;
import kr.flab.movieon.payment.domain.PaymentRedirectUri;

public final class NaverPayPaymentProvider implements PaymentProvider {

    @Override
    public boolean support(PaymentMethod paymentMethod) {
        return PaymentMethod.NAVER_PAY == paymentMethod;
    }

    @Override
    public PaymentRedirectUri pending(
        Payment payment) {
        // TODO - NaverPay 구현
        return null;
    }

    @Override
    public void pay(Payment payment) {
        // TODO - NaverPay 구현
        // 1. 결제 요청후, 응답받고
        // 2. 우리 payment 객체와 응답 비교 (도메인 로직)
        payment.complete();
    }
}
