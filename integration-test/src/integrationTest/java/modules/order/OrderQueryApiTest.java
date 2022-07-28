package modules.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import modules.IntegrateTestExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public final class OrderQueryApiTest extends IntegrateTestExtension {

    @Nested
    @DisplayName("주문 단건 조회")
    class FindOrderInfoApiTest {
        private static final String ORDER_URI = "/api/v1/orders/";
        private static final String ORDER_ID = "ord_202203101620471034855694";

        @Test
        @DisplayName("사용자가 자신의 주문 단건을 조회한다.")
        void successfully_order_info() throws Exception {
            final var action = mockMvc.perform(get(ORDER_URI + ORDER_ID)
                .header(AUTHORIZATION, BEARER + tokens.accessToken())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
            );

            action.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.body").exists());
        }
    }
}
