package kr.flab.movieon.product.application;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import kr.flab.movieon.product.domain.Category;
import kr.flab.movieon.product.domain.CategoryRepository;
import kr.flab.movieon.product.domain.Item;
import kr.flab.movieon.product.domain.ItemRepository;
import kr.flab.movieon.product.domain.Product;
import kr.flab.movieon.product.domain.ProductRepository;
import org.javaunit.autoparams.AutoSource;
import org.javaunit.autoparams.customization.Customization;
import org.javaunit.autoparams.customization.SettablePropertyWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

final class SpecsForProduct {

    @ParameterizedTest
    @AutoSource
    @Customization(SettablePropertyWriter.class)
    @DisplayName("상품 등록 과정에서 이용 등급이 잘못된 경우 에러가 발생합니다.")
    void name(RegisterProductCommand command) {
        // Arrange
        var sut = new ProductManager(new DummyProductRepository(), new DummyCategoryRepository(),
            new DummyItemRepository());

        // Act & Assert
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> sut.register(command));
    }

    private static final class DummyItemRepository implements ItemRepository {

        @Override
        public Item save(Item entity) {
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
