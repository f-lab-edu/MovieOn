package kr.flab.movieon.account.infrastructure.jpa;

import java.util.Optional;
import kr.flab.movieon.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<Account> findByAccountSubId(String id);
}
