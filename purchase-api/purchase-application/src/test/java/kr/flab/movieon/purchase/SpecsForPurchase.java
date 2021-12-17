package kr.flab.movieon.purchase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import kr.flab.movieon.purchase.application.PurchaseCommandService;
import kr.flab.movieon.purchase.domain.FakePurchaseRepository;
import kr.flab.movieon.purchase.domain.PaymentProcessor;
import kr.flab.movieon.purchase.domain.Purchase;
import kr.flab.movieon.purchase.domain.Purchase.PurchaseStatus;
import kr.flab.movieon.purchase.domain.Purchase.PurchaseType;
import kr.flab.movieon.purchase.domain.PurchaseCompletedEvent;
import kr.flab.movieon.purchase.domain.PurchaseFactory;
import kr.flab.movieon.purchase.integrate.Product;
import kr.flab.movieon.purchase.integrate.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
final class SpecsForPurchase {

    @Test
    @DisplayName("구매 대기 중인 상품을 결제하고, 최종 구매 결과를 검증한다.")
    void sut_purchased_voice_movie_product_with_a_5_days_usage_period() {
        // Arrange
        var purchaseRepository = new FakePurchaseRepository();
        setupPendingPurchaseEntity(purchaseRepository);
        var publisher = mock(ApplicationEventPublisher.class);
        var sut = new PurchaseCommandService(mock(ProductRepository.class), purchaseRepository,
            mock(PaymentProcessor.class), publisher);

        // Act
        Purchase expected = sut.payed(1L, "TOSS");

        // Assert
        assertThat(expected.getStatus()).isEqualTo(PurchaseStatus.SUCCESS);
        assertThat(expected.getType()).isEqualTo(PurchaseType.PURCHASE);

        verify(publisher, times(1))
            .publishEvent(Mockito.any(PurchaseCompletedEvent.class));
    }

    private void setupPendingPurchaseEntity(FakePurchaseRepository purchaseRepository) {
        var product = Product.builder()
            .productId(1L)
            .title("보이스")
            .type("PURCHASE")
            .availableDays(5)
            .price(BigDecimal.valueOf(16390))
            .build();
        purchaseRepository.save(PurchaseFactory.pending(1L, product.getProductId(),
            product.getTitle(), product.getPrice(),
            product.getAvailableDays(), product.getType()));
    }
}