package kr.flab.movieon.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_INPUT(400, "001", "Invalid Input"),
    UN_AUTHORIZED(401, "002", "UnAuthorized"),
    FORBIDDEN(403, "003", "Forbidden Request"),
    NOT_FOUND(404, "004", "Resource Not Found"),
    METHOD_NOW_ALLOWED(405, "005", "Method Now Allowed"),
    CONFLICT_WITH_EXISTING_VALUES(409, "006", "Conflict with existing value"),
    JWT_TOKEN_EXPIRED(409, "007", "Token Expired"),
    INVALID_TOKEN(409, "008", "Invalid Token"),
    UNSUPPORTED_MEDIA_TYPE(415, "009", "Unsupported Media Type"),
    SYSTEM_ERROR(500, "010", "Internal Server Error");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
