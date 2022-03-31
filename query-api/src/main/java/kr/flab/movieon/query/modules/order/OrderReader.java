package kr.flab.movieon.query.modules.order;

public interface OrderReader {

    OrderReadModel findByOrderId(String orderId);
}
