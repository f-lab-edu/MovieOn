package kr.flab.movieon.account.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "로그인 요청")
public record LoginAccountRequest(
    @NotBlank
    @Email
    @Schema(description = "이메일", example = "kitty123@gmail.com", required = true)
    String email,

    @NotBlank
    @Pattern(regexp = "^[A-Za-z1-9~!@#$%^&*()+|=]{8,12}$",
        message = "Please enter the password in English, numbers, "
            + "and special characters within 8-12 digits.")
    @Schema(description = "비밀번호", example = "12345678!", required = true)
    String password
) {

}
