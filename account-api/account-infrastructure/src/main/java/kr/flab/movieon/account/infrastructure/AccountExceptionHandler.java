package kr.flab.movieon.account.infrastructure;

import kr.flab.movieon.account.domain.exception.AccountNotFoundException;
import kr.flab.movieon.account.domain.exception.AccountNotVerifiedException;
import kr.flab.movieon.account.domain.exception.InvalidEmailTokenException;
import kr.flab.movieon.account.domain.exception.RegisterAccountConflictException;
import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.result.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "kr.flab.movieon.account")
public final class AccountExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AccountExceptionHandler.class);

    @ExceptionHandler(AccountNotFoundException.class)
    private ResponseEntity<ApiResponse<?>> handleAccountNotFound(
        AccountNotFoundException e) {
        log.error("AccountNotFoundException: ", e);

        return new ResponseEntity<>(ApiResponse.error(ErrorCode.INVALID_INPUT),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegisterAccountConflictException.class)
    private ResponseEntity<ApiResponse<?>> handleRegisterAccountConflict(
        RegisterAccountConflictException e) {
        log.error("RegisterAccountConflictException: ", e);

        return new ResponseEntity<>(ApiResponse.error(ErrorCode.CONFLICT_WITH_EXISTING_VALUES),
            HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccountNotVerifiedException.class)
    private ResponseEntity<ApiResponse<?>> handleAccountNotVerified(
        AccountNotVerifiedException e) {
        log.error("AccountNotVerifiedException: ", e);

        return new ResponseEntity<>(ApiResponse.error(ErrorCode.UN_AUTHORIZED),
            HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidEmailTokenException.class)
    private ResponseEntity<ApiResponse<?>> handleInvalidEmailToken(
        InvalidEmailTokenException e) {
        log.error("InvalidEmailTokenException: ", e);

        return new ResponseEntity<>(ApiResponse.error(ErrorCode.INVALID_TOKEN),
            HttpStatus.BAD_REQUEST);
    }
}
