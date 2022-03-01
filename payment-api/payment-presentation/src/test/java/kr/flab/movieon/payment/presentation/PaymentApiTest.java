package kr.flab.movieon.payment.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;

@DisplayName("결제 API")
@ExtendWith(MockitoExtension.class)
final class PaymentApiTest {

    @InjectMocks
    private PaymentApi paymentApi;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentApi)
            .build();
        objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("TossPayments 결제 승인 요청 API")
    final class TossPaymentsPaymentApprovalRequestTest {

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("결제 승인 요청 파라미터가 잘못된 경우 예외가 발생합니다.")
        void request_parameter_is_invalid(String arg) throws Exception {
            // Arrange
            var request = new LinkedMultiValueMap<String, String>();
            request.set("orderId", arg);
            request.set("paymentKey", arg);
            request.set("amount", "-1");

            // Act
            final var actions = mockMvc.perform(
                post("/api/v1/toss-payments/success")
                    .params(request)
            );

            // Assert
            actions
                .andExpect(status().isBadRequest());
        }

    }
}
