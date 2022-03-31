package kr.flab.movieon.query.modules.order;

import kr.flab.movieon.common.result.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class FindOrderApi {

    private final OrderReader orderReader;

    public FindOrderApi(OrderReader orderReader) {
        this.orderReader = orderReader;
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> findOrderInfo(@PathVariable String orderId) {
        var readModel = orderReader.findByOrderId(orderId);
        return ResponseEntity.ok(ApiResponse.success(readModel));
    }
}
