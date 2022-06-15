package kr.flab.movieon.account.infrastructure;

import kr.flab.movieon.account.domain.exception.DuplicatedEmailException;
import kr.flab.movieon.account.domain.exception.InvalidAccountException;
import kr.flab.movieon.account.domain.exception.PasswordNotMatchedException;
import kr.flab.movieon.account.infrastructure.jwt.AlreadyTokenExpiredException;
import kr.flab.movieon.account.infrastructure.jwt.RefreshTokenNotFoundException;
import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.InvalidArgumentException;
import kr.flab.movieon.common.error.InvalidTokenException;
import kr.flab.movieon.common.error.SystemException;
import kr.flab.movieon.common.result.ApiResponseEnvelop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "kr.flab.movieon.account")
public final class AccountModuleExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AccountModuleExceptionHandler.class);

    @ExceptionHandler(value = {
        AlreadyTokenExpiredException.class,
        RefreshTokenNotFoundException.class,
        InvalidTokenException.class,
        DuplicatedEmailException.class,
        PasswordNotMatchedException.class,
        InvalidArgumentException.class,
        InvalidAccountException.class
    })
    public ResponseEntity<ApiResponseEnvelop<?>> onError(SystemException e) {
        log.error("SystemError: ", e);
        if (e.getMessage() != null) {
            return ResponseEntity
                .status(toStatus(e.getErrorCode()))
                .body(ApiResponseEnvelop.error(e.getErrorCode(), e));
        }
        return ResponseEntity
            .status(toStatus(e.getErrorCode()))
            .body(ApiResponseEnvelop.error(e.getErrorCode()));
    }

    private HttpStatus toStatus(ErrorCode errorCode) {
        return HttpStatus.valueOf(errorCode.getStatus());
    }
}
