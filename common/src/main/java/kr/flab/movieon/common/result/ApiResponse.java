package kr.flab.movieon.common.result;

import java.util.Map;
import kr.flab.movieon.common.error.ErrorCode;

public final class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final String message;

    private ApiResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static ApiResponse<Map<String, Object>> error(ErrorCode error, String message) {
        return new ApiResponse<>(false, Map.of("status", error.getStatus(),
            "code", error.getCode(), "message", error.getMessage()), message);

    }

    public static ApiResponse<Map<String, Object>> error(ErrorCode error) {
        return new ApiResponse<>(false, Map.of("status", error.getStatus(),
            "code", error.getCode(), "message", error.getMessage()), error.getMessage());
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
