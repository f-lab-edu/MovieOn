package kr.flab.movieon.order.domain;

public interface OrderRepository {

    Order save(Order entity);

    Order findByOrderId(String orderId);
}
