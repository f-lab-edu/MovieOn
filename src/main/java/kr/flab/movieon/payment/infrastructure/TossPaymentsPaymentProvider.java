package kr.flab.movieon.payment.infrastructure;

import kr.flab.movieon.payment.domain.Payment;
import kr.flab.movieon.payment.domain.PaymentProvider;

public final class TossPaymentsPaymentProvider implements PaymentProvider {

    // Toss Payments 모듈 연동

    @Override
    public void payed(Payment payment) {
        // TODO 결제 처리 & 결제 공급자를 전략 패턴을 사용하여 Type에 따라 선택하게끔 리팩토링

        // 1. 결제 요청후, 응답받고
        // 2. 우리 payment 객체와 응답 비교 (도메인 로직)
        payment.complete();

        // 3. 다시 최종 결제 요청
    }
}
