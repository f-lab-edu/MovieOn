package kr.flab.movieon.payment.infrastructure.jpa;

import kr.flab.movieon.payment.domain.TossPayments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTossPaymentsRepository extends JpaRepository<TossPayments, Long> {

}
