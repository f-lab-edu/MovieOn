package kr.flab.movieon.payment.domain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class FakePaymentRepository implements PaymentRepository {

    private final Map<Long, Payment> data = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Payment save(Payment entity) {
        data.put(idGenerator.incrementAndGet(), entity);
        return entity;
    }

    @Override
    public Optional<Payment> findByPurchaseId(Long purchaseId) {
        return data.values().stream()
            .filter(entity -> entity.getPurchaseId().equals(purchaseId))
            .findFirst();
    }

}
