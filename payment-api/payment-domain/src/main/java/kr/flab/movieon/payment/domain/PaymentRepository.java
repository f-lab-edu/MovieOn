package kr.flab.movieon.payment.domain;

import java.util.Optional;

public interface PaymentRepository {

    Payment save(Payment entity);

    Optional<Payment> findByOrderId(Long orderId);
}
