package kr.flab.movieon.purchaser.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class FakePurchaserRepository implements PurchaserRepository {

    private final Map<Long, Purchaser> data = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Purchaser save(Purchaser entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
            data.put(entity.getId(), entity);
            return entity;
        } else {
            return data.putIfAbsent(entity.getId(), entity);
        }
    }

    @Override
    public Purchaser findById(Long purchaserId) {
        return data.values()
            .stream()
            .filter(p -> p.getId().equals(purchaserId))
            .findFirst()
            .orElse(null);
    }
}
