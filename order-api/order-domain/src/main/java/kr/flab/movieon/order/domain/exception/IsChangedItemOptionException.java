package kr.flab.movieon.order.domain.exception;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class IsChangedItemOptionException extends SystemException {

    public IsChangedItemOptionException(String message) {
        super(message, ErrorCode.CHANGED_ITEM_OPTION);
    }
}
