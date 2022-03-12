package kr.flab.movieon.order.infrastructure;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.InvalidArgumentException;
import kr.flab.movieon.common.error.SystemException;
import kr.flab.movieon.common.result.ApiResponse;
import kr.flab.movieon.order.domain.exception.AlreadyCanceledException;
import kr.flab.movieon.order.domain.exception.AmountNotMatchedException;
import kr.flab.movieon.order.domain.exception.IsChangedItemException;
import kr.flab.movieon.order.domain.exception.IsChangedItemOptionException;
import kr.flab.movieon.order.domain.exception.IsEmptyOrderItemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "kr.flab.movieon.order")
public final class OrderModuleExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderModuleExceptionHandler.class);

    @ExceptionHandler(value = {
        AmountNotMatchedException.class,
        AlreadyCanceledException.class,
        InvalidArgumentException.class,
        IsEmptyOrderItemException.class,
        IsChangedItemException.class,
        IsChangedItemOptionException.class
    })
    public ResponseEntity<ApiResponse<?>> onError(SystemException e) {
        log.error("SystemError: ", e);
        if (e.getMessage() != null) {
            return ResponseEntity
                .status(toStatus(e.getErrorCode()))
                .body(ApiResponse.error(e.getErrorCode(), e));
        }
        return ResponseEntity
            .status(toStatus(e.getErrorCode()))
            .body(ApiResponse.error(e.getErrorCode()));
    }

    private HttpStatus toStatus(ErrorCode errorCode) {
        return HttpStatus.valueOf(errorCode.getStatus());
    }
}
