package kr.flab.movieon.purchase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import kr.flab.movieon.product.domain.ProductRepository;
import kr.flab.movieon.purchase.application.PurchaseCommandService;
import kr.flab.movieon.purchase.domain.FakePurchaseRepository;
import kr.flab.movieon.purchase.domain.PaymentProcessor;
import kr.flab.movieon.purchase.domain.Purchase;
import kr.flab.movieon.purchase.domain.Purchase.PurchaseStatus;
import kr.flab.movieon.purchase.domain.Purchase.PurchaseType;
import kr.flab.movieon.purchase.domain.PurchasedProduct;
import kr.flab.movieon.purchase.domain.Purchaser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
final class SpecsForPurchase {

    @Test
    @DisplayName("구매 대기 중인 상품을 결제하고, 최종 구매 결과를 검증한다.")
    void sut_purchased_voice_movie_product_with_a_5_days_usage_period() {
        // Arrange
        var purchaseRepository = new FakePurchaseRepository();
        setupPendingPurchaseEntity(purchaseRepository);
        var sut = new PurchaseCommandService(mock(ProductRepository.class), purchaseRepository,
            mock(PaymentProcessor.class));

        // Act
        Purchase expected = sut.payed(1L);

        // Assert
        assertThat(expected.getStatus()).isEqualTo(PurchaseStatus.SUCCESS);
        assertThat(expected.getType()).isEqualTo(PurchaseType.PURCHASE);
    }

    private void setupPendingPurchaseEntity(FakePurchaseRepository purchaseRepository) {
        purchaseRepository.save(Purchase.pending(
            new Purchaser(1L),
            PurchasedProduct.create(1L, "보이스",
                BigDecimal.valueOf(16390), 5),
            "PURCHASE"));
    }
}
