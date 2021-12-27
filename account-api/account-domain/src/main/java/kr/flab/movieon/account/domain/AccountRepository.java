package kr.flab.movieon.account.domain;

import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findById(Long accountId);

    Optional<Account> findByUserId(String userId);

    Optional<Account> findByEmail(String email);

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);
}
