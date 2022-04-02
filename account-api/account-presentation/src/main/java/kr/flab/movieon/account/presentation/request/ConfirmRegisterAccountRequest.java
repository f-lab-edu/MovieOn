package kr.flab.movieon.account.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Schema(description = "회원가입 인증 확인 요청")
public final class ConfirmRegisterAccountRequest {

    @NotBlank
    @Schema(description = "인증 토큰", example = "example-token", required = true)
    private String token;

    @NotBlank
    @Email
    @Schema(description = "이메일", example = "kitty123@gmail.com", required = true)
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
