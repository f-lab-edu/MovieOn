package kr.flab.movieon.order.domain;

import java.util.List;

public interface ProductRepository {

    List<Product> findAllById(List<Long> productIds);
}
