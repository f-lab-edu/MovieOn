package kr.flab.movieon.common;

import lombok.Getter;

@Getter
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

    public static ApiResponse<ErrorCode> error(ErrorCode error, String message) {
        return new ApiResponse<>(false, error, message);
    }

    public static ApiResponse<ErrorCode> error(ErrorCode error) {
        return new ApiResponse<>(false, error, error.getMessage());
    }
}
