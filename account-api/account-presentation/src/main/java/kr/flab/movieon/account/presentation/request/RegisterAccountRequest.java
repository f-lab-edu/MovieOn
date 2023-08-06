package kr.flab.movieon.account.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "회원가입 요청")
public record RegisterAccountRequest(
    @Email @NotBlank
    @Schema(description = "이메일", example = "kitty123@gmail.com", required = true)
    String email,

    @NotBlank
    @Pattern(regexp = "^[A-Za-z1-9~!@#$%^&*()+|=]{8,12}$",
        message = "Please enter the password in English, numbers, "
            + "and special characters within 8-12 digits.")
    @Schema(description = "비밀번호", example = "12345678!", required = true)
    String password,

    @NotBlank
    @Schema(description = "닉네임", example = "kitty321", required = true)
    String username
) {}
