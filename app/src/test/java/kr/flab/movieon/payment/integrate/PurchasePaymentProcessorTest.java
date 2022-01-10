package kr.flab.movieon.payment.integrate;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import kr.flab.movieon.payment.domain.Payment;
import kr.flab.movieon.payment.domain.PaymentMethod;
import kr.flab.movieon.payment.domain.PaymentProvider;
import kr.flab.movieon.payment.domain.PaymentRedirectUri;
import kr.flab.movieon.payment.domain.PaymentStatus;
import kr.flab.movieon.payment.domain.PaymentValidator;
import kr.flab.movieon.purchase.domain.Purchase;
import kr.flab.movieon.purchase.domain.Purchase.PurchaseType;
import kr.flab.movieon.purchase.domain.PurchaseRepository;
import kr.flab.movieon.purchase.domain.PurchasedProduct;
import kr.flab.movieon.purchase.domain.Purchaser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PurchasePaymentProcessorTest {

    @Test
    @DisplayName("payment 결제대기 도메인 서비스 테스트")
    void paymentPendingTest() {
        // Arrange
        var purchaseRepository = new StubPurchaseRepository();
        var paymentRepository = new FakePaymentRepository();
        var paymentProvider = new FakePaymentProvider();

        var processor = new PurchasePaymentProcessor(
            purchaseRepository,
            paymentRepository,
            List.of(new DummyPaymentValidator()),
            List.of(paymentProvider)
        );
        var purchase = purchaseRepository.getStub();
        var purchaseId = 1L;

        // Act
        var redirectUrl = processor.pending(purchaseId, String.valueOf(PaymentMethod.TOSS));
        var payment = paymentRepository.findByPurchaseId(purchaseId);

        // Assert
        assertThat(payment).isPresent();
        assertThat(redirectUrl).isEqualTo(paymentProvider.getStubRedirectUri());
        assertThat(payment.get().getPurchaseId()).isEqualTo(purchase.getId());
        assertThat(payment.get().getStatus()).isEqualTo(PaymentStatus.PENDING);
    }

    @Test
    @DisplayName("payment 결제완료 도메인 서비스 테스트")
    void completePaymentTest() throws Exception {
        // Arrange
        var purchaseRepository = new StubPurchaseRepository();
        var paymentRepository = new FakePaymentRepository();

        var processor = new PurchasePaymentProcessor(
            purchaseRepository,
            paymentRepository,
            List.of(new DummyPaymentValidator()),
            List.of(new FakePaymentProvider())
        );
        var purchase = purchaseRepository.getStub();
        var purchaseId = 1L;
        var payment = paymentRepository.save(
            Payment.create(purchaseId, purchase.getTitle(), purchase.getPurchaserId(),
                purchase.getPrice(), PaymentMethod.TOSS));

        // Act
        processor.pay(purchaseId, payment.getPurchaseToken());

        // Assert
        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.PAYED);
    }

    private class DummyPaymentValidator implements PaymentValidator {

        @Override
        public void validate(Payment payment) {

        }
    }

    private class FakePaymentProvider implements PaymentProvider {

        private final PaymentRedirectUri redirectUri = new PaymentRedirectUri(
            "https://movie.on/api/payments/paySuccess");

        @Override
        public boolean support(PaymentMethod paymentMethod) {
            return true;
        }

        @Override
        public PaymentRedirectUri pending(Payment payment) {
            return redirectUri;
        }

        @Override
        public void pay(Payment payment) {
            payment.complete();
        }

        public PaymentRedirectUri getStubRedirectUri() {
            return redirectUri;
        }
    }

    private class StubPurchaseRepository implements PurchaseRepository {

        private final Purchase purchase;

        public StubPurchaseRepository() {
            this.purchase = createPurchase(2L, 2L, "제품", BigDecimal.valueOf(100), 10, "PURCHASE");
        }

        @Override
        public Purchase save(Purchase entity) {
            return this.purchase;
        }

        @Override
        public Purchase findById(Long purchaseId) {
            return this.purchase;
        }

        public Purchase getStub() {
            return this.purchase;
        }

        private Purchase createPurchase(Long purchaserId, Long productId, String title,
            BigDecimal price, int availableDays, String type) {
            var purchasedProduct = PurchasedProduct.create(productId, title, price, availableDays);
            var purchaser = new Purchaser(purchaserId);
            return new Purchase(purchaser, purchasedProduct, PurchaseType.valueOf(type));
        }
    }

}
