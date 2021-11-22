package kr.flab.movieon.module.payment.integrate;

import kr.flab.movieon.customer.domain.Customer;
import kr.flab.movieon.customer.domain.CustomerRepository;
import kr.flab.movieon.payment.domain.Payment;
import kr.flab.movieon.payment.domain.Payment.PaymentType;
import kr.flab.movieon.payment.domain.PaymentProvider;
import kr.flab.movieon.payment.domain.PaymentRepository;
import kr.flab.movieon.purchase.domain.PaymentProcessor;
import kr.flab.movieon.purchase.domain.Purchase;

public final class PurchasePaymentProcessor implements PaymentProcessor {

    private final CustomerRepository customerRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentProvider paymentProvider;

    public PurchasePaymentProcessor(
        CustomerRepository customerRepository,
        PaymentRepository paymentRepository,
        PaymentProvider paymentProvider) {
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
        this.paymentProvider = paymentProvider;
    }

    @Override
    public Purchase payed(Purchase purchase) {
        // TODO PaymentType을 어딘가를 통해서 받아오도록 리팩토링
        Customer customer = customerRepository.findById(purchase.getCustomerId());
        Payment payment = paymentRepository.save(
            Payment.create(purchase.getId(), purchase.getTitle(), customer.getName(),
                purchase.getPrice(), PaymentType.CARD));

        // TODO 결제 모듈 연동 및 결제 처리
        // TODO PaymentType에 따라서 provider가 정해지게끔
        paymentProvider.payed(payment);

        // TODO 구매 완료
        purchase.complete();
        // product.purchased(); 도메인 이벤트 전환
        return purchase;
    }
}
