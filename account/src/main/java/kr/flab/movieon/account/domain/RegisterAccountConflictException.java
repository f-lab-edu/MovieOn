package kr.flab.movieon.account.domain;

import kr.flab.movieon.common.ErrorCode;
import kr.flab.movieon.common.exception.SystemException;

public final class RegisterAccountConflictException extends SystemException {

    public RegisterAccountConflictException(String message) {
        super(message, ErrorCode.CONFLICT_WITH_EXISTING_VALUES);
    }

    public RegisterAccountConflictException() {
        super(ErrorCode.CONFLICT_WITH_EXISTING_VALUES);
    }
}
