package kr.flab.movieon.account.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class FakeAccountRepository implements AccountRepository {

    private final Map<Long, Account> data = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Account save(Account account) {
        return data.put(idGenerator.incrementAndGet(), account);
    }

    @Override
    public boolean existsByEmail(String email) {
        return data.values().stream()
            .anyMatch(account -> account.getEmail().equals(email));
    }

    @Override
    public Account findByEmail(String email) {
        return data.values().stream()
            .filter(account -> account.getEmail().equals(email))
            .findFirst()
            .get();
    }

    @Override
    public Account findById(Long accountId) {
        return data.values().stream()
            .filter(account -> account.getId().equals(accountId))
            .findFirst()
            .get();
    }

    @Override
    public boolean existsByUsername(String username) {
        return data.values().stream()
            .anyMatch(account -> account.getUsername().equals(username));
    }
}
