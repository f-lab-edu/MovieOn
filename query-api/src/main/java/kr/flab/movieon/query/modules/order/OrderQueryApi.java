package kr.flab.movieon.query.modules.order;

import kr.flab.movieon.common.result.ApiResponseEnvelop;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderQueryApi implements OrderQuerySpecification {

    private final OrderReader orderReader;

    public OrderQueryApi(OrderReader orderReader) {
        this.orderReader = orderReader;
    }

    @Override
    public ResponseEntity<ApiResponseEnvelop<OrderReadModel>> findOrderInfo(String orderId) {
        var readModel = orderReader.findByOrderId(orderId);
        return ResponseEntity.ok(ApiResponseEnvelop.success(readModel));
    }
}
