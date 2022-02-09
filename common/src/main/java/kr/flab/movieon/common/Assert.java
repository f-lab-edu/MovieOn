package kr.flab.movieon.common;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class Assert {

    private Assert() {

    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new SystemException(message, ErrorCode.INVALID_INPUT);
        }
    }
}
