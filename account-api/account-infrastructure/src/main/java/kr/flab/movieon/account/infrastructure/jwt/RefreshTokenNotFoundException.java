package kr.flab.movieon.account.infrastructure.jwt;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class RefreshTokenNotFoundException extends SystemException {

    public RefreshTokenNotFoundException() {
        super(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
    }
}
