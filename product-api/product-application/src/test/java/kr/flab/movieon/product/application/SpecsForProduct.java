package kr.flab.movieon.product.application;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.navercorp.fixturemonkey.FixtureMonkey;
import java.util.List;
import kr.flab.movieon.product.domain.Category;
import kr.flab.movieon.product.domain.CategoryRepository;
import kr.flab.movieon.product.domain.Item;
import kr.flab.movieon.product.domain.ItemRepository;
import kr.flab.movieon.product.domain.NotMatchedRateException;
import kr.flab.movieon.product.domain.Product;
import kr.flab.movieon.product.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class SpecsForProduct {

    @Test
    @DisplayName("상품 등록 과정에서 이용 등급이 잘못된 경우 에러가 발생합니다.")
    void name() {
        // Arrange
        var fixture = FixtureMonkey.create();
        var sut = new ProductManager(new DummyProductRepository(), new DummyCategoryRepository(),
            new DummyItemRepository());
        var command = fixture.giveMeOne(RegisterProductCommand.class);

        // Act & Assert
        assertThatExceptionOfType(NotMatchedRateException.class)
            .isThrownBy(() -> sut.register(command));
    }

    private static final class DummyItemRepository implements ItemRepository {

        @Override
        public Item save(Item entity) {
            return null;
        }

        @Override
        public List<Item> findAllById(List<Long> itemIds) {
            return null;
        }
    }

    private static final class DummyProductRepository implements ProductRepository {

        @Override
        public Product save(Product entity) {
            return null;
        }

        @Override
        public Product findById(Long productId) {
            return null;
        }
    }

    private static final class DummyCategoryRepository implements CategoryRepository {

        @Override
        public Category save(Category entity) {
            return null;
        }

        @Override
        public Category findById(Long categoryId) {
            return null;
        }
    }
}
