package modules.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.flab.movieon.order.application.request.CreateOrderRequest;
import modules.IntegrateTestExtension;
import org.javaunit.autoparams.AutoSource;
import org.javaunit.autoparams.customization.Customization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.http.MediaType;

public final class OrderIntegrationTest extends IntegrateTestExtension {

    @Nested
    @DisplayName("주문 생성 API")
    class CreateOrderApiTest {

        private static final String CREATE_ORDER_URI = "/api/v1/orders";

        @ParameterizedTest
        @AutoSource
        @Customization(CreateOrderRequestCustomization.class)
        @DisplayName("사용자가 주문을 생성하기 위해 적절한 요청을 보내고, 처리한 뒤에 주문 ID가 반환된다.")
        void create_order_processing_return_orderId(CreateOrderRequest request) throws Exception {
            // Act
            final var actions = mockMvc.perform(post(CREATE_ORDER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header(AUTHORIZATION, BEARER + tokens.getAccessToken())
            );

            // Assert
            actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.body").exists());
        }
    }
}
