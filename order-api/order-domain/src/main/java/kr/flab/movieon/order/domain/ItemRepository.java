package kr.flab.movieon.order.domain;

import java.util.List;

public interface ItemRepository {

    List<Item> findAllById(List<Long> productIds);
}
