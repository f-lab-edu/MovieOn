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
        var processor = new PaymentProcessor(List.of(new DummyPaymentApiProvider()),
            paymentRepository);
        var command = PaymentApprovalCommand.builder()
            .paymentType(Type.TOSS)
            .amount(BigDecimal.valueOf(10000L))
            .orderId(1L)
            .pgToken("token")
            .build();

        // Act
        processor.pay(command);

        var payment = paymentRepository.findByOrderId(command.getOrderId())
            .orElseThrow(() -> new IllegalStateException("cannot found payment, test fail"));

        // Assert
        assertThat(payment.getStatus()).isEqualTo(Status.PAYED);
        assertThat(payment.getAmount()).isEqualTo(command.getAmount());
        assertThat(payment.getType()).isEqualTo(command.getPaymentType());
    }

    private class DummyPaymentApiProvider implements PaymentApiProvider {

        @Override
        public boolean support(Type type) {
            return true;
        }

        @Override
        public Payment pay(PaymentApprovalCommand command) {
            var payment = Payment.create(
                command.getOrderId(),
                "item",
                2L,
                command.getAmount(),
                command.getPaymentType());

            payment.approve();
            return payment;
        }
    }
}
