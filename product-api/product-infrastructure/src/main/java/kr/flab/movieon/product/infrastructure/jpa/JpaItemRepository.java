package kr.flab.movieon.product.infrastructure.jpa;

import kr.flab.movieon.product.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaItemRepository extends JpaRepository<Item, Long> {

}
