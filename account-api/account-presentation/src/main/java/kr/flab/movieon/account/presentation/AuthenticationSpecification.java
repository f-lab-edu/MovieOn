package kr.flab.movieon.account.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.flab.movieon.account.presentation.request.LoginAccountRequest;
import kr.flab.movieon.account.presentation.request.RegisterAccountRequest;
import kr.flab.movieon.account.presentation.request.RegisterConfirmRequest;
import kr.flab.movieon.account.presentation.response.TokenResponse;
import kr.flab.movieon.common.result.ApiResponseEnvelop;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Authentication", description = "인증 API")
@SecurityRequirements()
public interface AuthenticationSpecification {

    @Operation(summary = "회원가입", description = "사용자가 회원가입하기 위한 Endpoint를 제공합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원가입 성공"),
        @ApiResponse(responseCode = "400", description = "회원가입 요청 파라미터 에러")
    })
    @PostMapping(value = "/api/v1/auth/register",
        produces = MediaType.APPLICATION_JSON_VALUE)
    void register(@RequestBody @Valid RegisterAccountRequest request);

    @Operation(summary = "회원가입 확인",
        description = "회원가입한 사용자가 올바른지 검증하는 Endpoint를 제공합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "303", description = "회원가입 처리 완료 및 리다이렉트"),
        @ApiResponse(responseCode = "400", description = "회원가입 확인 파라미터 에러")
    })
    @GetMapping(value = "/api/v1/auth/confirm")
    ResponseEntity<Void> registerConfirm(
        @ModelAttribute @Valid RegisterConfirmRequest request);

    @Operation(summary = "로그인", description = "회원가입 완료 사용자가 로그인하는 EndPoint를 제공합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그인 성공"),
        @ApiResponse(responseCode = "400", description = "로그인 요청 파라미터 에러")
    })
    @PostMapping(value = "/api/v1/auth/login",
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiResponseEnvelop<TokenResponse>> login(
        @RequestBody @Valid LoginAccountRequest request);

    @Operation(summary = "토큰 재발급", description = "토큰을 재발급하는 EndPoint를 제공합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
        @ApiResponse(responseCode = "400", description = "토큰 재발급 요청 에러")
    })
    @PostMapping(value = "/api/v1/auth/reIssuance",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiResponseEnvelop<TokenResponse>> reIssuance(
        @RequestHeader("Authorization") String payload);
}
