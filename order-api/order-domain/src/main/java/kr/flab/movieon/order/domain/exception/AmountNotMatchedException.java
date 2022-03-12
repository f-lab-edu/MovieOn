package kr.flab.movieon.order.domain.exception;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class AmountNotMatchedException extends SystemException {

    public AmountNotMatchedException(String message) {
        super(message, ErrorCode.MISS_MATCHED_AMOUNT);
    }
}
