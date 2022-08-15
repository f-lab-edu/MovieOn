package kr.flab.movieon.notification.domain;

import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public record RegisteredAccountEvent(String accountId, String username, String email,
                                     String emailCheckToken, Date occurredOn) implements
    DomainEvent {

    @Override
    public String toString() {
        return "RegisteredAccountEvent{" + "accountId='" + accountId + '\'' + ", username='"
            + username + '\'' + ", email='" + email + '\'' + ", emailCheckToken='" + emailCheckToken
            + '\'' + ", occurredOn=" + occurredOn + '}';
    }
}
