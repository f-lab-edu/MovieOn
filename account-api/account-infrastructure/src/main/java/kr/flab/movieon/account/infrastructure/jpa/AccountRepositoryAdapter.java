package kr.flab.movieon.account.infrastructure.jpa;

import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.ResourceNotFoundException;

public final class AccountRepositoryAdapter implements AccountRepository {

    private final JpaAccountRepository jpaAccountRepository;

    public AccountRepositoryAdapter(
        JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    @Override
    public boolean existsByEmail(String email) {
        // TODO Exists Query 최적화 필요
        return jpaAccountRepository.existsByEmail(email);
    }

    @Override
    public Account save(Account account) {
        return jpaAccountRepository.save(account);
    }

    @Override
    public Account findByEmail(String email) {
        return jpaAccountRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    @Override
    public Account findById(String accountId) {
        return jpaAccountRepository.findByAccountSubId(accountId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaAccountRepository.existsByUsername(username);
    }
}
