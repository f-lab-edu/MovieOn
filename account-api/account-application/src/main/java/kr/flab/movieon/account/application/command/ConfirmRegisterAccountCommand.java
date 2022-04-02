package kr.flab.movieon.account.application.command;

public final class ConfirmRegisterAccountCommand {

    private final String token;
    private final String email;

    public ConfirmRegisterAccountCommand(String token, String email) {
        this.token = token;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }
}
