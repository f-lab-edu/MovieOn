package kr.flab.movieon.account.infrastructure;

import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountRepository extends AccountRepository, JpaRepository<Account, Long> {

}
