package kr.flab.movieon.account.application.command;

public final class LoginAccountCommand {

    private final String email;
    private final String password;

    public LoginAccountCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
