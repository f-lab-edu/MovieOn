package kr.flab.movieon.order.domain.exception;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class AlreadyCanceledException extends SystemException {

    public AlreadyCanceledException(String message) {
        super(message, ErrorCode.ALREADY_CANCELED);
    }
}
