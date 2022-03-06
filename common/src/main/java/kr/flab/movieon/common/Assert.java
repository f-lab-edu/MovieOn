package kr.flab.movieon.common;

import kr.flab.movieon.common.error.InvalidArgumentException;

public final class Assert {

    private Assert() {

    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new InvalidArgumentException(message);
        }
    }
}
