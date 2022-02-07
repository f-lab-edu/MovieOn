package kr.flab.movieon.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Period;
import java.util.List;
import kr.flab.movieon.product.domain.ItemOption.ItemType;
import kr.flab.movieon.product.domain.ProductContentsDetail.Rate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class ProductTests {

    @Test
    @DisplayName("상품에 대한 판촉 활동 정보를 추가한다")
    void name() {
        // Arrange
        var product = voiceProduct();
        var item = voiceItem();

        // Act
        item.addOption(new ItemOption(BigDecimal.valueOf(14900),
            Period.ofDays(7), ItemType.PURCHASE));

        // Assert
        assertThat(item.getOptions().size()).isEqualTo(1);
    }

    private Item voiceItem() {
        return new Item(1L, "(구매) 보이스", "PC, 모바일, TV 총 5대",
            "최대 1080(FHD), 4.15GB", true, List.of("image.png"));
    }

    private Product voiceProduct() {
        return Product.builder()
            .id(1L)
            .name("보이스")
            .description("부산 건설현장 직원들을 상대로 걸려온 전화 한 통.")
            .category(new Category("액션"))
            .contentsDetail(ProductContentsDetail.builder()
                .director("김선,김곡")
                .actors("변요한,김무열,김희원,박명훈")
                .rate(Rate.PARENTAL_GUIDANCE_15)
                .runningTime(Duration.ofMinutes(109))
                .build())
            .thumbnails("voice.png")
            .build();
    }
}
