package kr.flab.movieon.query.modules.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.flab.movieon.common.AuthenticatedUser;
import kr.flab.movieon.common.result.ApiResponseEnvelop;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "AccountQuery", description = "회원 조회 API")
public interface AccountQuerySpecification {

    @Operation(summary = "회원 조회", description = "회원 정보를 조회하기 위한 Endpoint를 제공합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원 조회 성공"),
        @ApiResponse(responseCode = "404", description = "회원이 존재하지 않는 에러",
            content = @Content)
    })
    @GetMapping(value = "/api/v1/accounts/me",
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiResponseEnvelop<AccountReadModel>> findInfo(
        @AuthenticationPrincipal AuthenticatedUser user);
}
