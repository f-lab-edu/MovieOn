package kr.flab.movieon.common.error;

public final class InvalidTokenException extends SystemException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
