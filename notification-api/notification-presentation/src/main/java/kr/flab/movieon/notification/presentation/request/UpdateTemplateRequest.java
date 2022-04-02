package kr.flab.movieon.notification.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;

@Schema(description = "알림 템플릿 수정 요청")
public final class UpdateTemplateRequest {

    @Schema(description = "이메일 알림 제목", example = "회원가입 인증 메일입니다.")
    private String title;

    @NotEmpty
    @Schema(description = "알림 템플릿 내용", required = true)
    private String contents;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "UpdateTemplateRequest{" + "title='" + title + '\'' + ", contents='" + contents
            + '\'' + '}';
    }
}
