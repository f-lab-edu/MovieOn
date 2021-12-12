package kr.flab.movieon.account.domain.exception;

import kr.flab.movieon.common.ErrorCode;

public final class RegisterAccountConflictException extends SystemException {

    public RegisterAccountConflictException(String message) {
        super(message, ErrorCode.CONFLICT_WITH_EXISTING_VALUES);
    }

    public RegisterAccountConflictException() {
        super(ErrorCode.CONFLICT_WITH_EXISTING_VALUES);
    }
}
