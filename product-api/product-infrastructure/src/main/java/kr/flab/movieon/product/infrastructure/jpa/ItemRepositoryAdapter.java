package kr.flab.movieon.product.infrastructure.jpa;

import kr.flab.movieon.product.domain.Item;
import kr.flab.movieon.product.domain.ItemRepository;

public final class ItemRepositoryAdapter implements ItemRepository {

    private final JpaItemRepository jpaItemRepository;

    public ItemRepositoryAdapter(JpaItemRepository jpaItemRepository) {
        this.jpaItemRepository = jpaItemRepository;
    }

    @Override
    public Item save(Item entity) {
        return jpaItemRepository.save(entity);
    }
}
