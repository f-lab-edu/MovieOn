package kr.flab.movieon.account.infrastructure;

import kr.flab.movieon.account.domain.exception.AccountNotFoundException;
import kr.flab.movieon.account.domain.exception.RegisterAccountConflictException;
import kr.flab.movieon.common.ApiResponse;
import kr.flab.movieon.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "kr.flab.movieon.account")
public final class AccountExceptionHandler {

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

}
