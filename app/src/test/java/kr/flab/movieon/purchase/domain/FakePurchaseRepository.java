package kr.flab.movieon.purchase.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class FakePurchaseRepository implements PurchaseRepository {

    private final Map<Long, Purchase> data = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Purchase save(Purchase entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
            data.put(entity.getId(), entity);
            return entity;
        } else {
            return data.putIfAbsent(entity.getId(), entity);
        }
    }

    @Override
    public Purchase findById(Long purchaseId) {
        return data.values()
            .stream()
            .filter(p -> p.getId().equals(purchaseId))
            .findFirst()
            .orElse(null);
    }
}
