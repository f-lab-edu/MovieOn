package kr.flab.movieon.account.domain.exception;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class DuplicatedUsernameException extends SystemException {

    public DuplicatedUsernameException() {
        super(ErrorCode.DUPLICATE_USERNAME);
    }
}
