package kr.flab.movieon.account.infrastructure.jwt;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class TokenExpiredException extends SystemException {

    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
