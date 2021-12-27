package kr.flab.movieon.account.infrastructure;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.AccountRepository;

public final class FakeAccountRepository implements AccountRepository {

    private final Map<Long, Account> data = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Account save(Account account) {
        return data.put(idGenerator.incrementAndGet(), account);
    }

    @Override
    public Optional<Account> findById(Long accountId) {
        if (!data.containsKey(accountId)) {
            return Optional.empty();
        }
        return Optional.ofNullable(data.get(accountId));
    }

    @Override
    public Optional<Account> findByUserId(
        String userId) {
        return data.values().stream()
            .filter(account -> userId.equals(account.getUserId()))
            .findAny();
    }

    @Override
    public Optional<Account> findByEmail(
        String email) {
        return data.values().stream()
            .filter(account -> email.equals(account.getEmail()))
            .findAny();
    }

    @Override
    public boolean existsByUserId(String userId) {
        return data.values().stream()
            .anyMatch(account -> account.getUserId().equals(userId));
    }

    @Override
    public boolean existsByEmail(String email) {
        return data.values().stream()
            .anyMatch(account -> account.getEmail().equals(email));
    }
}
