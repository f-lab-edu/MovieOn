package kr.flab.movieon.order.domain.exception;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class IsChangedItemException extends SystemException {

    public IsChangedItemException(String message) {
        super(message, ErrorCode.CHANGED_ITEM);
    }
}
