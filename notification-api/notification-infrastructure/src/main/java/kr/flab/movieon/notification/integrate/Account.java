package kr.flab.movieon.notification.integrate;

public final class Account {

    private final String email;
    private final String username;

    public Account(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
