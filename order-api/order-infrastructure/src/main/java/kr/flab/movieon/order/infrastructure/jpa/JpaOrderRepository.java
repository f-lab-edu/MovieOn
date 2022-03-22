package kr.flab.movieon.order.infrastructure.jpa;

import java.util.Optional;
import kr.flab.movieon.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderSubId(String orderSubId);
}
