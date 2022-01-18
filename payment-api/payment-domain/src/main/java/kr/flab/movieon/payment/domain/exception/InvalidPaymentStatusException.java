package kr.flab.movieon.payment.domain.exception;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class InvalidPaymentStatusException extends SystemException {

    public InvalidPaymentStatusException() {
        super(ErrorCode.INVALID_INPUT);
    }

    public InvalidPaymentStatusException(String message) {
        super(message, ErrorCode.INVALID_INPUT);
    }
}
