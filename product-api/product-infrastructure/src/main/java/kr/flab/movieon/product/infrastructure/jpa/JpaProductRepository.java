package kr.flab.movieon.product.infrastructure.jpa;

import kr.flab.movieon.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, Long> {

}
