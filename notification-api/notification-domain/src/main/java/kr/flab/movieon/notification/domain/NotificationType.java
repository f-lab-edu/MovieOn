package kr.flab.movieon.notification.domain;

import java.util.Arrays;
import kr.flab.movieon.common.error.InvalidArgumentException;

public enum NotificationType {
    EMAIL, SMS, PUSH;

    public static NotificationType findByType(String typeName) {
        return Arrays.stream(values())
            .filter(t -> t.name().equals(typeName))
            .findFirst()
            .orElseThrow(() -> new InvalidArgumentException("일치하는 type을 찾을 수 없습니다."));
    }
}
