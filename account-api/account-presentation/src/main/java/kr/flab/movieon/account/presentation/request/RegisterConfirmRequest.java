package kr.flab.movieon.account.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Schema(description = "회원가입 인증 확인 요청")
public record RegisterConfirmRequest(
    @NotBlank
    @Schema(description = "인증 토큰", example = "example-token", required = true)
    String token,
    @NotBlank @Email
    @Schema(description = "이메일", example = "kitty123@gmail.com", required = true)
    String email
) {}
