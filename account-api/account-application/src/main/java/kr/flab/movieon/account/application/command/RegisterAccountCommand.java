package kr.flab.movieon.account.application.command;

public final class RegisterAccountCommand {

    private final String email;
    private final String password;
    private final String username;

    public RegisterAccountCommand(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
