package kr.flab.movieon.payment.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class FakePaymentRepository implements PaymentRepository {

    private final Map<Long, Payment> data = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Payment save(Payment entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
            data.put(entity.getId(), entity);
            return entity;
        } else {
            return data.putIfAbsent(entity.getId(), entity);
        }
    }
}
