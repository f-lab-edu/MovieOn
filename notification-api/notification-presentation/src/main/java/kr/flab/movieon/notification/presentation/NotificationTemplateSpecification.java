package kr.flab.movieon.notification.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import kr.flab.movieon.notification.presentation.request.CreateTemplateRequest;
import kr.flab.movieon.notification.presentation.request.UpdateTemplateRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Notification Template", description = "알림 템플릿 API")
public interface NotificationTemplateSpecification {

    @Operation(summary = "알림 템플릿 생성",
        description = "관리자가 알림 템플릿을 생성하기 위한 Endpoint를 제공합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "알림 템플릿 생성 성공"),
        @ApiResponse(responseCode = "400", description = "알림 템플릿 생성 요청 에러")
    })
    @PostMapping(value = "/api/v1/notifications/templates",
        produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void create(@RequestBody @Valid CreateTemplateRequest request);

    @Operation(summary = "알림 템플릿 수정",
        description = "관리자가 알림 템플릿을 수정하기 위한 Endpoint를 제공합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "알림 템플릿 수정 성공"),
        @ApiResponse(responseCode = "400", description = "알림 템플릿 수정 요청 에러")
    })
    @PutMapping(value = "/api/v1/notifications/templates/{templateId}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void update(
        @Parameter(description = "템플릿ID", example = "1", required = true)
        @PathVariable Long templateId,
        @RequestBody @Valid UpdateTemplateRequest request);
}
