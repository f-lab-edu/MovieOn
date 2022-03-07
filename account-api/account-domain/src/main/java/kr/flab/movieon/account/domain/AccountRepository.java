package kr.flab.movieon.account.domain;

public interface AccountRepository {

    boolean existsByEmail(String email);

    Account save(Account account);

    Account findByEmail(String email);

    Account findById(String accountId);

    boolean existsByUsername(String username);
}
