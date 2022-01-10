package kr.flab.movieon.payment.infrastructure;

import kr.flab.movieon.payment.domain.Payment;
import kr.flab.movieon.payment.domain.PaymentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends PaymentRepository, JpaRepository<Payment, Long> {

}
