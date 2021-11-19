package kr.flab.movieon.purchase.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.Year;
import kr.flab.movieon.common.Days;
import kr.flab.movieon.product.domain.FakeProductRepository;
import kr.flab.movieon.product.domain.MovieDisplay;
import kr.flab.movieon.product.domain.Product;
import kr.flab.movieon.product.domain.Product.ProductType;
import kr.flab.movieon.purchase.domain.FakePurchaseRepository;
import kr.flab.movieon.purchase.domain.Purchase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class SpecsForPurchase {

    @Test
    @DisplayName("특정 고객이 이용기간이 5일인 보이스를 구매하고, 구매 상태와 내역을 검증한다.")
    void contextLoads() {
        // Arrange
        var productRepository = new FakeProductRepository();
        productRepository.save(voiceProduct());
        var purchaseRepository = new FakePurchaseRepository();
        var sut = new PurchaseCommandService(productRepository, purchaseRepository);
        var actual = actualSuccessPurchase();

        // Act
        Purchase expected = sut.purchase(1L, 1L);

        // Assert
        assertThat(expected.getStatus()).isEqualTo(actual.getStatus());
        assertThat(expected.getType()).isEqualTo(actual.getType());
        assertThat(expected.getHistory()).isEqualTo(actual.getHistory());
    }

    private Product voiceProduct() {
        return new Product(
            MovieDisplay.builder()
                .title("보이스")
                .actors("변요한")
                .description("설명")
                .release(Year.of(2021))
                .director("김선,김곡")
                .runningTime(LocalTime.of(2, 15, 30))
                .build(),
            BigDecimal.valueOf(16390),
            new Days(5),
            ProductType.PURCHASE
        );
    }

    private Purchase actualSuccessPurchase() {
        Purchase purchase = Purchase.create(1L, 1L, BigDecimal.valueOf(16390),
            "PURCHASE");
        purchase.setId(1L);
        purchase.proceed(5);
        return purchase;
    }
}
