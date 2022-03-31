package kr.flab.movieon.query.modules.account;

import java.time.LocalDateTime;

public final class AccountReadModel {

    private String accountId;
    private String email;
    private String username;
    private LocalDateTime joinedAt;

    public String getAccountId() {
        return accountId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }
}
