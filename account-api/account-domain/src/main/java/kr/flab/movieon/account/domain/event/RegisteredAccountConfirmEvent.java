package kr.flab.movieon.account.domain.event;

import java.util.Date;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.common.domain.model.DomainEvent;
import lombok.Getter;

@Getter
public final class RegisteredAccountConfirmEvent implements DomainEvent {

    private final Long accountId;
    private final String email;
    private final String emailValidationToken;
    private final Date occurredOn;

    public RegisteredAccountConfirmEvent(Account account) {
        this.accountId = account.getId();
        this.email = account.getEmail();
        this.emailValidationToken = account.getEmailValidationToken();
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }
}
