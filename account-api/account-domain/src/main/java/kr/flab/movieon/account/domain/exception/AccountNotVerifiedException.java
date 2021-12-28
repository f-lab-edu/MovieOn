package kr.flab.movieon.account.domain.exception;

import kr.flab.movieon.common.ErrorCode;
import kr.flab.movieon.common.SystemException;

public final class AccountNotVerifiedException extends SystemException {

    public AccountNotVerifiedException() {
        super("Account's email has not been verified. Please confirm register email token.",
            ErrorCode.UN_AUTHORIZED);
    }
}
