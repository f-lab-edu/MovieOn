package kr.flab.movieon.account.infrastructure;

import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.account.domain.exception.RegisterAccountConflictException;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class RegisterAccountProcessorImpl implements RegisterAccountProcessor {

    private final AccountRepository accountRepository;
    private final PasswordEncoder encoder;

    public RegisterAccountProcessorImpl(
        AccountRepository accountRepository,
        PasswordEncoder encoder) {
        this.accountRepository = accountRepository;
        this.encoder = encoder;
    }

    @Override
    public void register(String userId, String password, String email) {

        if (accountRepository.existsByUserId(userId)) {
            throw new RegisterAccountConflictException("Error: User id is already in use");
        }

        if (accountRepository.existsByEmail(email)) {
            throw new RegisterAccountConflictException("Error: Email is already in use");
        }

        var account = Account.of(userId, email, encoder.encode(password));
        accountRepository.save(account);
    }

}
