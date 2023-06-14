package kr.flab.movieon.order.presentation;

import java.net.URI;
import kr.flab.movieon.common.AuthenticatedUser;
import kr.flab.movieon.common.result.ApiResponseEnvelop;
import kr.flab.movieon.order.application.OrderFacade;
import kr.flab.movieon.order.presentation.request.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class OrderApi implements OrderSpecification {

    private final OrderFacade orderFacade;
    private final String baseUrl;

    public OrderApi(OrderFacade orderFacade,
                    @Value("${app.url}") String baseUrl) {
        this.orderFacade = orderFacade;
        this.baseUrl = baseUrl;
    }

    @Override
    public ResponseEntity<ApiResponseEnvelop<URI>> create(CreateOrderRequest request,
        AuthenticatedUser user) {
        var info = orderFacade.create(user.id(), request.toCommand());
        URI uri = URI.create(baseUrl + "/api/v1/orders/" + info);
        return ResponseEntity.ok(ApiResponseEnvelop.success(uri));
    }
}
