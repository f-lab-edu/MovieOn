package kr.flab.movieon.account.domain;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class DuplicatedEmailException extends SystemException {

    public DuplicatedEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
