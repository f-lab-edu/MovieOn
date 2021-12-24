package kr.flab.movieon.integrate.security.exception;

import kr.flab.movieon.account.domain.exception.SystemException;
import kr.flab.movieon.common.ErrorCode;

public final class InvalidTokenException extends SystemException {

    public InvalidTokenException(String message) {
        super(message, ErrorCode.INVALID_TOKEN);
    }

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
