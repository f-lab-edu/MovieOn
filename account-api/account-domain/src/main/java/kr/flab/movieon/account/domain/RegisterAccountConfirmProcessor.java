package kr.flab.movieon.account.domain;

import kr.flab.movieon.account.domain.exception.AccountNotFoundException;
import kr.flab.movieon.account.domain.exception.InvalidEmailTokenException;

public final class RegisterAccountConfirmProcessor {

    private final AccountRepository accountRepository;

    public RegisterAccountConfirmProcessor(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void confirm(String token, String email) {
        var account = accountRepository.findByEmail(email)
            .orElseThrow(AccountNotFoundException::new);

        if (!account.isValidEmailToken(token)) {
            throw new InvalidEmailTokenException();
        }

        account.confirmRegister();
    }
}
