package kr.flab.movieon.product.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class FakeProductRepository implements ProductRepository {

    private final Map<Long, Product> data = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Product save(Product entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
            data.put(entity.getId(), entity);
            return entity;
        } else {
            return data.putIfAbsent(entity.getId(), entity);
        }
    }

    @Override
    public Product findById(Long productId) {
        return data.values()
            .stream()
            .filter(p -> p.getId().equals(productId))
            .findFirst()
            .orElse(null);
    }
}
