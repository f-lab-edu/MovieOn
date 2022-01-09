package kr.flab.movieon.payment.domain.exception;

import kr.flab.movieon.common.ErrorCode;
import kr.flab.movieon.common.SystemException;

public final class InvalidPaymentCommandException extends SystemException {

    public InvalidPaymentCommandException() {
        super(ErrorCode.INVALID_INPUT);
    }

    public InvalidPaymentCommandException(String message) {
        super(message, ErrorCode.INVALID_INPUT);
    }
}
