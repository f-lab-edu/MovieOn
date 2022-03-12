package kr.flab.movieon.product.domain;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class NotMatchedRateException extends SystemException {

    public NotMatchedRateException(String message) {
        super(message, ErrorCode.NOT_MATCHED_RATE);
    }
}
