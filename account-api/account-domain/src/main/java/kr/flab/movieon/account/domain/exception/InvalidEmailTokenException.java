package kr.flab.movieon.account.domain.exception;

import kr.flab.movieon.common.ErrorCode;
import kr.flab.movieon.common.SystemException;

public final class InvalidEmailTokenException extends SystemException {

    public InvalidEmailTokenException() {
        super("Fail register confirm. This email token is not valid.", ErrorCode.INVALID_TOKEN);
    }

    public InvalidEmailTokenException(String message) {
        super(message, ErrorCode.INVALID_TOKEN);
    }

}
