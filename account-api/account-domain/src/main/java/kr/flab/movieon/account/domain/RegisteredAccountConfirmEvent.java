package kr.flab.movieon.account.domain;

import java.util.Date;
import kr.flab.movieon.common.DomainEvent;
import lombok.Getter;

@Getter
public final class RegisteredAccountConfirmEvent implements DomainEvent {

    private final Long accountId;
    private final String email;
    private final String emailCheckToken;
    private final Date occurredOn;

    public RegisteredAccountConfirmEvent(Long accountId, String email,
        String emailCheckToken) {
        this.accountId = accountId;
        this.email = email;
        this.emailCheckToken = emailCheckToken;
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }
}
