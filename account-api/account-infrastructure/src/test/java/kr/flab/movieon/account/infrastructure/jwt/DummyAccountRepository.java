package kr.flab.movieon.account.infrastructure.jwt;

import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.AccountRepository;

public final class DummyAccountRepository implements AccountRepository {

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public Account findByEmail(String email) {
        return null;
    }

    @Override
    public Account findById(Long accountId) {
        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }
}
