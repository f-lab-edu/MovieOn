package modules.payment;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.flab.movieon.payment.application.TossPaymentsPaymentApprovalRequest;
import kr.flab.movieon.payment.infrastructure.TossPaymentsPaymentCompletedResponse;
import modules.IntegrateTestExtension;
import org.javaunit.autoparams.AutoSource;
import org.javaunit.autoparams.customization.Customization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

public final class PaymentIntegrateTest extends IntegrateTestExtension {

    @Nested
    @DisplayName("Toss Payments API")
    class TossPaymentsApiTest {

        private static final String TOSS_PAYMENTS_SUCCESS_URI = "/api/v1/toss-payments/success";

        @ParameterizedTest
        @AutoSource
        @Customization(TossPaymentsPaymentCompletedResponseCustomization.class)
        @DisplayName("결제 승인에 필요한 파라미터가 입력되고, 결제가 승인된다.")
        void name(TossPaymentsPaymentCompletedResponse response) throws Exception {
            // Arrange
            var request = new TossPaymentsPaymentApprovalRequest();
            request.setAmount(21700);
            request.setOrderId("ord_202203101620471034855694");
            request.setPaymentKey("fake-key");

            BDDMockito.doNothing().when(verifier).verify(Mockito.any());
            BDDMockito.when(approvalProcessor.approval(Mockito.any()))
                .thenReturn(response);

            // Act
            final var actions = mockMvc.perform(post(TOSS_PAYMENTS_SUCCESS_URI)
                .header(AUTHORIZATION, BEARER + tokens.getAccessToken())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            );

            // Assert
            actions.andDo(print())
                .andExpect(status().isOk());
        }
    }

}
