package kr.flab.movieon.common;

import kr.flab.movieon.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public final class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ApiResponse<ErrorCode>> handleArgumentNotValid(
        MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: ", e);

        return new ResponseEntity<>(ApiResponse.error(ErrorCode.INVALID_INPUT),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<ApiResponse<ErrorCode>> handleMethodNotSupported(
        HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException", e);

        return new ResponseEntity<>(ApiResponse.error(ErrorCode.METHOD_NOW_ALLOWED),
            HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ApiResponse<ErrorCode>> handleArgumentException(
        IllegalArgumentException e) {
        log.error("IllegalArgumentException", e);

        return new ResponseEntity<>(ApiResponse.error(ErrorCode.INVALID_INPUT),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    private ResponseEntity<ApiResponse<ErrorCode>> handleIllegalState(IllegalStateException e) {
        log.error("IllegalStateException", e);

        return new ResponseEntity<>(ApiResponse.error(ErrorCode.INVALID_INPUT),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    private ResponseEntity<ApiResponse<ErrorCode>> handleMediaTypeNotSupported(
        HttpMediaTypeNotSupportedException e) {
        log.error("HttpMediaTypeNotSupportedException", e);
        return new ResponseEntity<>(ApiResponse.error(ErrorCode.UNSUPPORTED_MEDIA_TYPE),
            HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<ApiResponse<ErrorCode>> handleAuthenticationException(
        AuthenticationException e) {
        log.error("AuthenticationException", e);
        return new ResponseEntity<>(ApiResponse.error(ErrorCode.UN_AUTHORIZED),
            HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<ApiResponse<ErrorCode>> handleAccessDenied(
        AccessDeniedException e) {
        log.error("AccessDeniedException", e);

        return new ResponseEntity<>(ApiResponse.error(ErrorCode.FORBIDDEN),
            HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SystemException.class)
    private ResponseEntity<ApiResponse<ErrorCode>> handleSystemException(SystemException e) {
        log.error("SystemException", e);

        return new ResponseEntity<>(ApiResponse.error(e.getErrorCode()),
            HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ApiResponse<ErrorCode>> handleException(Exception e) {
        log.error("Unknown Exception", e);

        return new ResponseEntity<>(ApiResponse.error(ErrorCode.SYSTEM_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
