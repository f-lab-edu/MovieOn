package kr.flab.movieon.account.infrastructure.security.exception;

import kr.flab.movieon.common.ErrorCode;
import kr.flab.movieon.common.exception.SystemException;

public final class InvalidTokenException extends SystemException {

    public InvalidTokenException(String message) {
        super(message, ErrorCode.INVALID_TOKEN);
    }

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
