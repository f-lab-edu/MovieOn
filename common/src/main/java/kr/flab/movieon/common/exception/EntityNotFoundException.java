package kr.flab.movieon.common.exception;

import kr.flab.movieon.common.ErrorCode;

public class EntityNotFoundException extends SystemException {

    public EntityNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.NOT_FOUND);
    }

    public EntityNotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}
