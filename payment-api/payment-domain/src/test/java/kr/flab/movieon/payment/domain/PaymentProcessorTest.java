package kr.flab.movieon.payment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import kr.flab.movieon.payment.domain.Payment.Status;
import kr.flab.movieon.payment.domain.Payment.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaymentProcessorTest {

    @Test
    @DisplayName("payment processor 도메인 서비스 테스트")
    void paymentApprovalTest() {
        // Arrange
        var paymentRepository = new FakePaymentRepository();
        var processor = new PaymentProcessor(List.of(new FakePaymentProvider()), paymentRepository);
        var command = PaymentApprovalCommand.builder()
            .paymentType(Type.TOSS)
            .amount(BigDecimal.valueOf(10000L))
            .purchaseId(1L)
            .build();

        // Act
        processor.pay(command, "token");

        var payment = paymentRepository.findByPurchaseId(command.getPurchaseId())
            .orElseThrow(() -> new IllegalStateException("cannot found payment, test fail"));

        // Assert
        assertThat(payment.getStatus()).isEqualTo(Status.PAYED);
        assertThat(payment.getAmount()).isEqualTo(command.getAmount());
        assertThat(payment.getType()).isEqualTo(command.getPaymentType());
        assertThat(payment.getQuantity()).isEqualTo(1L);
    }

    private class FakePaymentProvider implements PaymentProvider {

        @Override
        public boolean support(Type type) {
            return true;
        }

        @Override
        public boolean validate(PaymentApprovalCommand command, String pgToken) {
            return true;
        }

        @Override
        public Payment pay(PaymentApprovalCommand command, String pgToken) {
            var payment = Payment.create(
                command.getPurchaseId(),
                "item",
                2L,
                command.getAmount(),
                command.getPaymentType());

            payment.complete();
            return payment;
        }
    }
}
