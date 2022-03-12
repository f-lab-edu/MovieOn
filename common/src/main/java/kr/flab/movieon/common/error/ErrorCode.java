package kr.flab.movieon.common.error;

public enum ErrorCode {

    // System error
    INVALID_INPUT(400, "You have entered an invalid value."),
    UN_AUTHENTICATED(401, "Unauthenticated approach."),
    UN_DEFINED_ERROR(500, "A temporary error has occurred. Please try again in a little while."),
    METHOD_NOT_ALLOWED(405, "Http method is not allowed."),
    UNSUPPORTED_MEDIA_TYPE(415, "Un supported media type."),
    UN_HANDLED(400, "Un handled error."),
    ACCESS_DENIED(403, "Access denied."),

    // Account module error
    ACCOUNT_NOT_FOUND(404, "The account does not exist."),
    ACCOUNT_TOKEN_NOT_VERIFIED(400,
        "Account's email has not been verified. Please confirm register email token."),
    ACCOUNT_INVALID_EMAIL_TOKEN(400, "Email token is invalid."),
    DUPLICATE_EMAIL(400, "Duplicate email."),
    DUPLICATE_USERNAME(400, "Duplicate username."),
    PASSWORD_NOT_MATCHED(400, "Password is not matched."),
    INVALID_ACCOUNT(400, "Invalid account."),

    // Jwt, mail token error
    INVALID_TOKEN(400, "Invalid token."),
    NOT_FOUND_REFRESH_TOKEN(404, "Refresh token is not found."),
    TOKEN_EXPIRED(400, "Token expired."),

    // Order module error
    ALREADY_CANCELED(400, "Already canceled order."),
    MISS_MATCHED_AMOUNT(400, "Payment amount and the order amount do not match."),
    EMPTY_ORDER_ITEMS(400, "Order item is empty."),
    CHANGED_ITEM(400, "Item information is changed."),
    CHANGED_ITEM_OPTION(400, "Item option is changed."),

    // Product module error
    NOT_MATCHED_RATE(400, "Not matched rate."),

    DISABLED_NOTIFICATION_GROUP(400, "This notification group is disabled.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
