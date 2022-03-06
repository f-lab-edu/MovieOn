package kr.flab.movieon.account.application.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public final class ConfirmRegisterAccountCommand {

    @NotBlank
    private String token;
    @NotBlank
    @Email
    private String email;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
