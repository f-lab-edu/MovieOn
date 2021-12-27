package kr.flab.movieon.account.domain.exception;

import kr.flab.movieon.common.ErrorCode;
import kr.flab.movieon.common.SystemException;

public final class AccountNotFoundException extends SystemException {

    public AccountNotFoundException() {
        super("Account could not be found", ErrorCode.NOT_FOUND);
    }

    public AccountNotFoundException(String message) {
        super(message, ErrorCode.NOT_FOUND);
    }
}
