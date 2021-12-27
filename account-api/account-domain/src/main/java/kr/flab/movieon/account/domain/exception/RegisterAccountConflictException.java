package kr.flab.movieon.account.domain.exception;

import kr.flab.movieon.common.ErrorCode;
import kr.flab.movieon.common.SystemException;

public final class RegisterAccountConflictException extends SystemException {

    public RegisterAccountConflictException() {
        super(ErrorCode.CONFLICT_WITH_EXISTING_VALUES);
    }

    public RegisterAccountConflictException(String message) {
        super(message, ErrorCode.CONFLICT_WITH_EXISTING_VALUES);
    }
}
