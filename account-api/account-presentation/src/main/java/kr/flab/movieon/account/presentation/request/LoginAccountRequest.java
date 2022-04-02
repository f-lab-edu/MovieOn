package kr.flab.movieon.account.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "로그인 요청")
public final class LoginAccountRequest {

    @NotBlank
    @Email
    @Schema(description = "이메일", example = "kitty123@gmail.com", required = true)
    private String email;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z1-9~!@#$%^&*()+|=]{8,12}$",
        message = "Please enter the password in English, numbers, "
            + "and special characters within 8-12 digits.")
    @Schema(description = "비밀번호", example = "12345678!", required = true)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
