package kr.flab.movieon.account.domain;

public final class LoginAccountProcessor {

    private final AccountRepository accountRepository;
    private final PasswordEncrypter passwordEncrypter;

    public LoginAccountProcessor(AccountRepository accountRepository,
        PasswordEncrypter passwordEncrypter) {
        this.accountRepository = accountRepository;
        this.passwordEncrypter = passwordEncrypter;
    }

    public Account login(String email, String rawPassword) {
        var account = accountRepository.findByEmail(email);
        if (!passwordEncrypter.matches(rawPassword, account.getPassword())) {
            throw new PasswordNotMatchedException();
        }

        if (!account.isEmailVerified()) {
            throw new InvalidAccountException("User email has not verified.");
        }

        return account;
    }
}
