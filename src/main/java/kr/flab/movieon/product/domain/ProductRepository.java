package kr.flab.movieon.product.domain;

public interface ProductRepository {

    Product save(Product entity);

    Product findById(Long productId);
}
