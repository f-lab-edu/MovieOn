package kr.flab.movieon.account.domain;

import kr.flab.movieon.common.error.InvalidTokenException;

public final class RegisterAccountProcessor {

    private final AccountRepository accountRepository;
    private final PasswordEncrypter encrypter;

    public RegisterAccountProcessor(AccountRepository accountRepository,
        PasswordEncrypter encrypter) {
        this.accountRepository = accountRepository;
        this.encrypter = encrypter;
    }

    public Account register(String email, String password, String username) {
        if (accountRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
        if (accountRepository.existsByUsername(username)) {
            throw new DuplicatedUsernameException();
        }

        var account = Account.register(email, encrypter.encode(password), username);
        accountRepository.save(account);

        return account;
    }

    public Account registerConfirm(String token, String email) {
        var account = accountRepository.findByEmail(email);
        if (!account.isValidToken(token)) {
            throw new InvalidTokenException();
        }
        account.completeRegister();
        return account;
    }
}
