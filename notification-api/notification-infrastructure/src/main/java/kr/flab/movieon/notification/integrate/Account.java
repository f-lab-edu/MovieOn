package kr.flab.movieon.notification.integrate;

public final class Account {

    private final String email;
    private final String userId;

    public Account(String email, String userId) {
        this.email = email;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }
}
