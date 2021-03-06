package kr.flab.movieon.notification.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import kr.flab.movieon.notification.application.NotificationTemplateManager.CreateTemplateCommand;

@Schema(description = "알림 템플릿 생성 요청")
public record CreateTemplateRequest(

    @NotEmpty
    @Schema(description = "알림 타입명", example = "EMAIL", required = true)
    String typeName,

    @NotEmpty
    @Schema(description = "템플릿명", example = "회원가입 인증 메일입니다.", required = true)
    String templateName,

    @Schema(description = "이메일 알림 제목", example = "MovieOn 회원가입 메일입니다.")
    String title,

    @NotEmpty
    @Schema(description = "알림 템플릿 내용", required = true)
    String contents
) {

    public CreateTemplateCommand toCommand() {
        return new CreateTemplateCommand(this.typeName, this.templateName, this.title,
            this.contents);
    }
}
