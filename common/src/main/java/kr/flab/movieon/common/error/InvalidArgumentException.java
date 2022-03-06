package kr.flab.movieon.common.error;

public final class InvalidArgumentException extends SystemException {

    public InvalidArgumentException() {
        super(ErrorCode.INVALID_INPUT);
    }

    public InvalidArgumentException(String message) {
        super(message, ErrorCode.INVALID_INPUT);
    }
}
