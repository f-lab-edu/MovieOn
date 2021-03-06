package kr.flab.movieon.common.result;

import kr.flab.movieon.common.error.ErrorCode;
import kr.flab.movieon.common.error.SystemException;

public final class ApiResponseEnvelop<T> {

    private final boolean success;
    private final T body;

    private ApiResponseEnvelop(boolean success, T body) {
        this.success = success;
        this.body = body;
    }

    public static <T> ApiResponseEnvelop<T> success(T body) {
        return new ApiResponseEnvelop<>(true, body);
    }

    public static ApiResponseEnvelop<ErrorBody> error(ErrorCode errorCode) {
        return new ApiResponseEnvelop<>(false,
            new ErrorBody(errorCode.name(), errorCode.getMessage()));
    }

    public static ApiResponseEnvelop<ErrorBody> error(ErrorCode errorCode,
        SystemException exception) {
        return new ApiResponseEnvelop<>(false,
            new ErrorBody(errorCode.name(), exception.getMessage()));
    }

    public static <T> ApiResponseEnvelop<T> error(T body) {
        return new ApiResponseEnvelop<>(false, body);
    }

    public boolean isSuccess() {
        return success;
    }

    public T getBody() {
        return body;
    }

    public static final class ErrorBody {

        private final String code;
        private final String message;

        public ErrorBody(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
