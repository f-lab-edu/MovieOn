package kr.flab.movieon;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Year;
import kr.flab.movieon.common.Days;
import kr.flab.movieon.payment.domain.FakePaymentRepository;
import kr.flab.movieon.payment.infrastructure.PaymentProviderDelegator;
import kr.flab.movieon.payment.integrate.PurchasePaymentProcessor;
import kr.flab.movieon.product.domain.FakeProductRepository;
import kr.flab.movieon.product.domain.MovieDisplay;
import kr.flab.movieon.product.domain.Product;
import kr.flab.movieon.product.domain.Product.ProductType;
import kr.flab.movieon.purchase.application.PurchaseCommandService;
import kr.flab.movieon.purchase.domain.FakePurchaseRepository;
import kr.flab.movieon.purchase.domain.Purchase;
import kr.flab.movieon.purchase.domain.Purchase.PurchaseStatus;
import kr.flab.movieon.purchase.domain.Purchase.PurchaseType;
import kr.flab.movieon.purchase.domain.PurchasedProduct;
import kr.flab.movieon.purchase.domain.Purchaser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class SpecsForPurchase {

    @Test
    @DisplayName("구매 대기 중인 상품을 결제하고, 최종 구매 결과를 검증한다.")
    void sut_purchased_voice_movie_product_with_a_5_days_usage_period() {
        // Arrange
        var productRepository = new FakeProductRepository();
        var purchaseRepository = new FakePurchaseRepository();
        setupPendingPurchaseEntity(purchaseRepository);
        var paymentCommandProcessor = getPaymentCommandProcessor();
        var sut = new PurchaseCommandService(productRepository, purchaseRepository,
            paymentCommandProcessor);

        // Act
        Purchase expected = sut.payed(1L);

        // Assert
        assertThat(expected.getStatus()).isEqualTo(PurchaseStatus.SUCCESS);
        assertThat(expected.getType()).isEqualTo(PurchaseType.PURCHASE);
    }

    private void setupPendingPurchaseEntity(FakePurchaseRepository purchaseRepository) {
        var purchase = Purchase.pending(
            new Purchaser(1L),
            PurchasedProduct.create(1L, "보이스",
                BigDecimal.valueOf(16390), 5),
            "PURCHASE");
        purchaseRepository.save(purchase);
    }

    private PurchasePaymentProcessor getPaymentCommandProcessor() {
        var paymentRepository = new FakePaymentRepository();
        var paymentProviderDelegator = new PaymentProviderDelegator();
        return new PurchasePaymentProcessor(paymentRepository, paymentProviderDelegator);
    }

    private Product voiceProduct() {
        return new Product(
            MovieDisplay.builder()
                .title("보이스")
                .actors("변요한")
                .description("설명")
                .release(Year.of(2021))
                .director("김선,김곡")
                .runningTime(Duration.ofMinutes(149))
                .build(),
            BigDecimal.valueOf(16390),
            new Days(5),
            ProductType.PURCHASE
        );
    }
}
