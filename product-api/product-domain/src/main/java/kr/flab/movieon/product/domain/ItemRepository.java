package kr.flab.movieon.product.domain;

import java.util.List;

public interface ItemRepository {

    Item save(Item entity);

    List<Item> findAllById(List<Long> itemIds);
}
