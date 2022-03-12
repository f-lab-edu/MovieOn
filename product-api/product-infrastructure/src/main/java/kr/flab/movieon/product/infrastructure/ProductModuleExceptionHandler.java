package kr.flab.movieon.product.infrastructure;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.InvalidArgumentException;
import kr.flab.movieon.common.error.SystemException;
import kr.flab.movieon.common.result.ApiResponse;
import kr.flab.movieon.product.domain.NotMatchedRateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "kr.flab.movieon.product")
public final class ProductModuleExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ProductModuleExceptionHandler.class);

    @ExceptionHandler(value = {
        InvalidArgumentException.class,
        NotMatchedRateException.class
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
