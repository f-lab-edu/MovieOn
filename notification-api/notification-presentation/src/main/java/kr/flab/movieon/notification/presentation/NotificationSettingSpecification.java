package kr.flab.movieon.notification.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.flab.movieon.common.AuthenticatedUser;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Notification Setting", description = "알림 설정 API")
public interface NotificationSettingSpecification {

    @Operation(summary = "특정 그룹 활성화",
        description = "사용자가 특정 그룹을 활성화하기 위한 Endpoint를 제공합니다.")
    @PatchMapping(value = "/{groupName}/enable",
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "그룹 활성화 성공"),
        @ApiResponse(responseCode = "400", description = "그룹 활성화 요청 에러")
    })
    void enable(
        @Parameter(description = "그룹타입", example = "PURCHASE_INFO", required = true)
        @PathVariable String groupName,
        @AuthenticationPrincipal AuthenticatedUser user);

    @Operation(summary = "세부 그룹 활성화",
        description = "사용자가 그룹 내 세부 그룹을 활성화하기 위한 Endpoint를 제공합니다.")
    @PatchMapping(value = "/{groupName}/enable/{typeName}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    void enable(
        @Parameter(description = "그룹타입", example = "PURCHASE_INFO", required = true)
        @PathVariable String groupName,
        @Parameter(description = "알림타입", example = "EMAIL", required = true)
        @PathVariable String typeName,
        @AuthenticationPrincipal AuthenticatedUser user);

    @Operation(summary = "특정 그룹 비활성화",
        description = "사용자가 특정 그룹을 비활성화하기 위한 Endpoint를 제공합니다.")
    @PatchMapping(value = "/{groupName}/disable",
        produces = MediaType.APPLICATION_JSON_VALUE)
    void disable(
        @Parameter(description = "그룹타입", example = "PURCHASE_INFO", required = true)
        @PathVariable String groupName,
        @AuthenticationPrincipal AuthenticatedUser user);

    @Operation(summary = "세부 그룹 비활성화",
        description = "사용자가 그룹 내 세부 그룹을 비활성화하기 위한 Endpoint를 제공합니다.")
    @PatchMapping(value = "/{groupName}/disable/{typeName}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    void disable(
        @Parameter(description = "그룹타입", example = "PURCHASE_INFO", required = true)
        @PathVariable String groupName,
        @Parameter(description = "알림타입", example = "EMAIL", required = true)
        @PathVariable String typeName,
        @AuthenticationPrincipal AuthenticatedUser user);
}
