package kr.flab.movieon.notification.domain;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class IsDisabledNotificationGroupException extends SystemException {

    public IsDisabledNotificationGroupException(String message) {
        super(message, ErrorCode.DISABLED_NOTIFICATION_GROUP);
    }
}
