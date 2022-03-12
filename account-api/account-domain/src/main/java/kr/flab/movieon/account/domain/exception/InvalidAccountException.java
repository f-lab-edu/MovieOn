package kr.flab.movieon.account.domain.exception;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class InvalidAccountException extends SystemException {

    public InvalidAccountException(String message) {
        super(message, ErrorCode.INVALID_ACCOUNT);
    }
}
