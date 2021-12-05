package kr.flab.movieon.account.infrastructure.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;

@Getter
@JsonFormat(shape = Shape.OBJECT)
public enum ErrorCode {
    INVALID_INPUT(400, "1", "Invalid Input"),
    UN_AUTHORIZED(401, "2", "UnAuthorized"),
    FORBIDDEN(403, "3", "Forbidden Request"),
    NOT_FOUND(404, "4", "Resource Not Found"),
    METHOD_NOW_ALLOWED(405, "5", "Method Now Allowed"),
    JWT_TOKEN_EXPIRED(409, "6", "Token Expired"),
    INVALID_TOKEN(409, "7", "Invalid Token");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
