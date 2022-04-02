package kr.flab.movieon.supports;

import io.swagger.v3.oas.annotations.Hidden;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import kr.flab.movieon.MovieOnApplication;
import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.ResourceNotFoundException;
import kr.flab.movieon.common.result.ApiResponseEnvelop;
import kr.flab.movieon.common.result.ApiResponseEnvelop.ErrorBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestController
@RestControllerAdvice(basePackageClasses = MovieOnApplication.class)
public final class GlobalExceptionHandler implements ErrorController {

    private static final String SERVLET_ERROR_CODE = "javax.servlet.error.status_code";

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @RequestMapping("/error")
    public ResponseEntity<ApiResponseEnvelop<?>> onError(HttpServletRequest req) {
        log.error("Unhandled Servlet Exception : {}",
            req.getRequestURI() + "?" + req.getQueryString());
        Integer errorCode = (Integer) req.getAttribute(SERVLET_ERROR_CODE);
        return ResponseEntity.status(HttpStatus.valueOf(errorCode))
            .body(ApiResponseEnvelop.error(ErrorCode.UN_HANDLED));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseEnvelop<?>> onError(ResourceNotFoundException exception) {
        log.debug("ResourceNotFoundException: {}", exception.getMessage());
        return ResponseEntity.status(exception.getErrorCode().getStatus())
            .body(ApiResponseEnvelop.error(exception.getErrorCode()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponseEnvelop<ErrorBody>> onError(
        HttpRequestMethodNotSupportedException exception) {
        log.debug("HttpRequestMethodNotSupportedException: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(ApiResponseEnvelop.error(ErrorCode.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponseEnvelop<ErrorBody>> onError(
        HttpMediaTypeNotSupportedException exception) {
        log.debug("HttpMediaTypeNotSupportedException: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .body(ApiResponseEnvelop.error(ErrorCode.UNSUPPORTED_MEDIA_TYPE));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseEnvelop<ErrorBody>> onError(
        MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException: {}",
            loggingField(exception.getBindingResult()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponseEnvelop.error(ErrorCode.INVALID_INPUT));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponseEnvelop<ErrorBody>> onError(BindException exception) {
        log.error("MethodArgumentNotValidException: {}",
            loggingField(exception.getBindingResult()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponseEnvelop.error(ErrorCode.INVALID_INPUT));
    }

    private String loggingField(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream().map(this::format)
            .collect(Collectors.joining("  "));
    }

    private String format(FieldError f) {
        if (f.getField().equals("password")) {
            return "Field : [" + f.getField() + "] " + "Reason: [" + f.getDefaultMessage() + "]";
        }
        return "Field : [" + f.getField() + "] " + "Value: [" + f.getRejectedValue() + "]";
    }
}
