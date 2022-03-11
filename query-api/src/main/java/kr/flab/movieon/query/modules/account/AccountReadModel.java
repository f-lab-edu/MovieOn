package kr.flab.movieon.query.modules.account;

public class AccountReadModel {

    private String accountId;
    private String email;
    private String username;

    protected AccountReadModel() {

    }

    public AccountReadModel(String accountId, String email, String username) {
        this.accountId = accountId;
        this.email = email;
        this.username = username;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
