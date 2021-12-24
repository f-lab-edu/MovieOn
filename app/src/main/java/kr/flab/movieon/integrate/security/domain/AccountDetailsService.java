package kr.flab.movieon.integrate.security.domain;

import kr.flab.movieon.account.domain.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public AccountDetailsService(
        AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        var account = accountRepository.findByUserId(userId)
            .orElseThrow(() -> new UsernameNotFoundException("Account not found with user id: "
                + userId));

        return new AccountContext(account);
    }
}
