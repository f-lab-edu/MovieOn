package kr.flab.movieon.account.infrastructure.jpa;

import java.util.Optional;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.AccountRepository;

public final class AccountRepositoryAdapter implements AccountRepository {

    private final JpaAccountRepository jpaAccountRepository;

    public AccountRepositoryAdapter(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    @Override
    public Account save(Account account) {
        return jpaAccountRepository.save(account);
    }

    @Override
    public Optional<Account> findById(Long accountId) {
        return jpaAccountRepository.findById(accountId);
    }

    @Override
    public Optional<Account> findByUserId(String userId) {
        return jpaAccountRepository.findByUserId(userId);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return jpaAccountRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUserId(String userId) {
        // TODO Exists 쿼리 최적화 필요
        return jpaAccountRepository.existsByUserId(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        // TODO Exists 쿼리 최적화 필요
        return jpaAccountRepository.existsByEmail(email);
    }
}
