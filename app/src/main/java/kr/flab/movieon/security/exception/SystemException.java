package kr.flab.movieon.security.exception;

import kr.flab.movieon.common.ErrorCode;

public class SystemException extends RuntimeException {

    private final ErrorCode errorCode;

    public SystemException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public SystemException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
