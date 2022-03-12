package kr.flab.movieon.order.domain.exception;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class IsEmptyOrderItemException extends SystemException {

    public IsEmptyOrderItemException(String message) {
        super(message, ErrorCode.EMPTY_ORDER_ITEMS);
    }
}
