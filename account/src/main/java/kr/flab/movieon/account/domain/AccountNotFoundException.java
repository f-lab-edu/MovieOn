package kr.flab.movieon.account.domain;

import kr.flab.movieon.common.exception.EntityNotFoundException;

public final class AccountNotFoundException extends EntityNotFoundException {

    public AccountNotFoundException() {
        super("Account could not be found");
    }

}
