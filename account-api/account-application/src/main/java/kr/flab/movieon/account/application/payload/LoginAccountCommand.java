package kr.flab.movieon.account.application.payload;

import javax.validation.constraints.NotBlank;

public class LoginAccountCommand {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    public LoginAccountCommand(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginAccountCommand{" + "userId='" + userId + '\'' + ", password='" + password
            + '\'' + '}';
    }
}
