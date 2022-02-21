package kr.flab.movieon.account.application.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public final class RegisterConfirmCommand {

    @NotBlank
    private String token;

    @Email
    @NotBlank
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

    @Override
    public String toString() {
        return "RegisterConfirmCommand{" + "token='" + token + '\'' + ", email='" + email + '\''
            + '}';
    }
}
