package kr.flab.movieon.common.error;

public final class ResourceNotFoundException extends SystemException {

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
