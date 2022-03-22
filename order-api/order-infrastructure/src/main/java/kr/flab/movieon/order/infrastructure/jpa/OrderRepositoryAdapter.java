package kr.flab.movieon.order.infrastructure.jpa;

import kr.flab.movieon.order.domain.Order;
import kr.flab.movieon.order.domain.OrderRepository;

public final class OrderRepositoryAdapter implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;

    public OrderRepositoryAdapter(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Override
    public Order save(Order entity) {
        return jpaOrderRepository.save(entity);
    }

    @Override
    public Order findByOrderId(String orderId) {
        return jpaOrderRepository.findByOrderSubId(orderId)
            .orElseThrow(IllegalArgumentException::new);
    }
}
