package kr.flab.movieon.customer.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class FakeCustomerRepository implements CustomerRepository {

    private final Map<Long, Customer> data = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Customer save(Customer entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
            data.put(entity.getId(), entity);
            return entity;
        } else {
            return data.putIfAbsent(entity.getId(), entity);
        }
    }

    @Override
    public Customer findById(Long id) {
        return data.values()
            .stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
}
