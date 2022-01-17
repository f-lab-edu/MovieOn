package kr.flab.movieon.account.domain;

import kr.flab.movieon.account.domain.exception.RegisterAccountConflictException;

public final class RegisterAccountProcessor {

    private final AccountRepository accountRepository;
    private final PasswordEncrypter encrypter;

    public RegisterAccountProcessor(AccountRepository accountRepository,
        PasswordEncrypter encrypter) {
        this.accountRepository = accountRepository;
        this.encrypter = encrypter;
    }

    public void register(String userId, String email, String password) {
        if (accountRepository.existsByUserId(userId)) {
            throw new RegisterAccountConflictException("Error: User id is already in use");
        }

        if (accountRepository.existsByEmail(email)) {
            throw new RegisterAccountConflictException("Error: Email is already in use");
        }

        var account = Account.create(userId, email, encrypter.encode(password));

        accountRepository.save(account);
    }
}
