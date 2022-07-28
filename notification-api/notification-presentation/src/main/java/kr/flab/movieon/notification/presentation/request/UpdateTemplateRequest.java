package kr.flab.movieon.notification.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;

@Schema(description = "알림 템플릿 수정 요청")
public record UpdateTemplateRequest(
    @Schema(description = "이메일 알림 제목", example = "회원가입 인증 메일입니다.")
    String title,

    @NotEmpty
    @Schema(description = "알림 템플릿 내용", required = true)
    String contents
) {
}
