package modules.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import kr.flab.movieon.order.presentation.request.CreateOrderItemOptionRequest;
import kr.flab.movieon.order.presentation.request.CreateOrderLineItemRequest;
import kr.flab.movieon.order.presentation.request.CreateOrderRequest;
import modules.IntegrateTestExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public final class OrderIntegrationTest extends IntegrateTestExtension {

    @Nested
    @DisplayName("주문 생성 API")
    class OrderApiTest {

        private static final String CREATE_ORDER_URI = "/api/v1/orders";

        @Test
        @DisplayName("사용자가 주문을 생성하기 위해 적절한 요청을 보내고, 처리한 뒤에 주문 ID가 반환된다.")
        void create_order_processing_return_orderId() throws Exception {
            // Act
            final var actions = mockMvc.perform(post(CREATE_ORDER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(factory()))
                .header(AUTHORIZATION, BEARER + tokens.accessToken())
            );

            // Assert
            actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.body").exists());
        }

        private CreateOrderRequest factory() {
            var options = new CreateOrderItemOptionRequest();
            options.setOptionName("480P 화질");
            options.setSalesPrice(2000);
            var lineItem = new CreateOrderLineItemRequest();
            lineItem.setItemId(1L);
            lineItem.setBasePrice(11000L);
            lineItem.setProductName("(구매) 마이웨이");
            lineItem.setOptions(List.of(options));
            var request = new CreateOrderRequest();
            request.setLineItems(List.of(lineItem));
            request.setPayMethod("CARD");
            request.setUseOfPoint(1200L);
            return request;
        }
    }
}
