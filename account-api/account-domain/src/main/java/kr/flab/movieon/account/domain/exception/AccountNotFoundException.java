package kr.flab.movieon.account.domain.exception;

public final class AccountNotFoundException extends EntityNotFoundException {

    public AccountNotFoundException() {
        super("Account could not be found");
    }

}
