package kr.flab.movieon.account.presentation.payload;

import kr.flab.movieon.account.infrastructure.util.ErrorCode;
import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    private ErrorResponse(HttpStatus status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ErrorResponse of(ErrorCode error) {
        return new ErrorResponse(
            HttpStatus.resolve(error.getStatus()),
            error.getCode(),
            error.getMessage()
        );
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
