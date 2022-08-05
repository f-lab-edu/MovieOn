package modules.payment;

import static java.time.LocalDateTime.now;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.navercorp.fixturemonkey.FixtureMonkey;
import kr.flab.movieon.payment.infrastructure.toss.TossPaymentsCardInfoResponse;
import kr.flab.movieon.payment.infrastructure.toss.TossPaymentsPaymentCompletedResponse;
import kr.flab.movieon.payment.presentation.request.TossPaymentsPaymentApprovalRequest;
import modules.IntegrateTestExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public final class PaymentIntegrateTest extends IntegrateTestExtension {

    @Nested
    @DisplayName("Toss Payments API")
    class TossPaymentsApiTest {

        private static final String TOSS_PAYMENTS_SUCCESS_URI = "/api/v1/toss-payments/success";

        @Test
        @DisplayName("결제 승인에 필요한 파라미터가 입력되고, 결제가 승인된다.")
        void name() throws Exception {
            // Arrange
            FixtureMonkey fixture = FixtureMonkey.create();
            final String orderId = fixture.giveMeOne(String.class);
            final String paymentKey = fixture.giveMeOne(String.class);
            final Integer amount = fixture.giveMeOne(Integer.class);
            var request = new TossPaymentsPaymentApprovalRequest("ord_202203101620471034855694",
                "fake-key", 21700);

            doNothing().when(verifier).verify(orderId, paymentKey, amount);
            when(approvalProcessor.approval(orderId, paymentKey, amount)).thenReturn(
                factory(card()));

            // Act
            final var actions = mockMvc.perform(post(TOSS_PAYMENTS_SUCCESS_URI)
                .header(AUTHORIZATION, BEARER + tokens.accessToken())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            );

            // Assert
            actions.andDo(print())
                .andExpect(status().isOk());
        }

        private TossPaymentsCardInfoResponse card() {
            var card = new TossPaymentsCardInfoResponse();
            card.setCompany("무비온");
            card.setCardType("신용");
            card.setAcquireStatus("READY");
            card.setOwnerType("개인");
            card.setUseCardPoint(false);
            card.setApproveNo("00000000");
            card.setNumber("433012******1234");
            card.setInstallmentPlanMonths(0);
            card.setInterestFree(false);
            card.setReceiptUrl("https://merchants.tosspayments.com/web/serve/"
                + "merchant/test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq/receipt/fake-key");
            return card;
        }

        private TossPaymentsPaymentCompletedResponse factory(TossPaymentsCardInfoResponse card) {
            var response = new TossPaymentsPaymentCompletedResponse();
            response.setApprovedAt(now());
            response.setVersion("1.3");
            response.setPaymentKey("fake-key");
            response.setMid("tosspayments");
            response.setOrderId("ord_202203101620471034855694");
            response.setOrderName("(구매) 마이웨이");
            response.setType("NORMAL");
            response.setMethod("카드");
            response.setCurrency("KRW");
            response.setUseEscrow(false);
            response.setCultureExpense(false);
            response.setTotalAmount(7800);
            response.setBalanceAmount(7800);
            response.setSuppliedAmount(7120);
            response.setStatus("DONE");
            response.setSecret(null);
            response.setRequestedAt(now());
            response.setVat(0);
            response.setTaxFreeAmount(0);
            response.setTransactionKey(null);
            response.setCard(card);
            return response;
        }
    }

}
