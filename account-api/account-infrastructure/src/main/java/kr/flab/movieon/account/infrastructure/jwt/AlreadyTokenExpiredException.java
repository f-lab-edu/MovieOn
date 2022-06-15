package kr.flab.movieon.account.infrastructure.jwt;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class AlreadyTokenExpiredException extends SystemException {

    public AlreadyTokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
