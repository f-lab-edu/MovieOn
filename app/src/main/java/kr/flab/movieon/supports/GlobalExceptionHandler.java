package kr.flab.movieon.supports;

import java.util.stream.Collectors;
import kr.flab.movieon.MovieOnApplication;
import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.ResourceNotFoundException;
import kr.flab.movieon.common.result.ApiResponse;
import kr.flab.movieon.common.result.ApiResponse.ErrorBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MovieOnApplication.class)
public final class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> onError(
        ResourceNotFoundException exception) {
        return ResponseEntity
            .status(exception.getErrorCode().getStatus())
            .body(ApiResponse.error(exception.getErrorCode()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<ErrorBody>> onError(
        HttpRequestMethodNotSupportedException exception) {
        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(ApiResponse.error(ErrorCode.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<ErrorBody>> onError(
        HttpMediaTypeNotSupportedException exception) {
        return ResponseEntity
            .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .body(ApiResponse.error(ErrorCode.UNSUPPORTED_MEDIA_TYPE));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorBody>> onError(
        MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException: {}",
            loggingField(exception.getBindingResult()));
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(ErrorCode.INVALID_INPUT));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<ErrorBody>> onError(
        BindException exception) {
        log.error("MethodArgumentNotValidException: {}",
            loggingField(exception.getBindingResult()));
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(ErrorCode.INVALID_INPUT));
    }

    private String loggingField(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
            .stream()
            .map(this::format)
            .collect(Collectors.joining("  "));
    }

    private String format(FieldError f) {
        if (f.getField().equals("password")) {
            return "Field : [" + f.getField() + "] " + "Reason: [" + f.getDefaultMessage() + "]";
        }
        return "Field : [" + f.getField() + "] " + "Value: [" + f.getRejectedValue() + "]";
    }
}
