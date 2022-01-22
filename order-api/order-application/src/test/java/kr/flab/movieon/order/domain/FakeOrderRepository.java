package kr.flab.movieon.order.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class FakeOrderRepository implements OrderRepository {

    private final Map<Long, Order> data = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Order save(Order entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
            data.put(entity.getId(), entity);
            return entity;
        } else {
            return data.putIfAbsent(entity.getId(), entity);
        }
    }

    @Override
    public Order findByOrderId(String orderId) {
        return null;
    }
}
