package kr.flab.movieon.notification.domain;

import java.util.Arrays;

public enum NotificationType {
    EMAIL, SMS, PUSH;

    public static NotificationType findByType(String typeName) {
        return Arrays.stream(values())
            .filter(t -> t.name().equals(typeName))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("일치하는 type을 찾을 수 없습니다."));
    }
}
