package kr.flab.movieon.notification.infrastructure;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.InvalidArgumentException;
import kr.flab.movieon.common.error.SystemException;
import kr.flab.movieon.common.result.ApiResponseEnvelop;
import kr.flab.movieon.notification.domain.IsDisabledNotificationGroupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "kr.flab.movieon.notification")
public final class NotificationModuleExceptionHandler {

    private static final Logger log = LoggerFactory
        .getLogger(NotificationModuleExceptionHandler.class);

    @ExceptionHandler(value = {
        InvalidArgumentException.class,
        IsDisabledNotificationGroupException.class
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
