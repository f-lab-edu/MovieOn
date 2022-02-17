package kr.flab.movieon.product.infrastructure.jpa;

import kr.flab.movieon.product.domain.Product;
import kr.flab.movieon.product.domain.ProductRepository;

public final class ProductRepositoryAdapter implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

    public ProductRepositoryAdapter(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public Product save(Product entity) {
        return jpaProductRepository.save(entity);
    }

    @Override
    public Product findById(Long productId) {
        return jpaProductRepository.findById(productId)
            .orElseThrow(IllegalArgumentException::new);
    }
}
