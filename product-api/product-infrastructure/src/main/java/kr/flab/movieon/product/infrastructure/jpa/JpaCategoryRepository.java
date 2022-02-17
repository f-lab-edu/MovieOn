package kr.flab.movieon.product.infrastructure.jpa;

import kr.flab.movieon.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<Category, Long> {

}
