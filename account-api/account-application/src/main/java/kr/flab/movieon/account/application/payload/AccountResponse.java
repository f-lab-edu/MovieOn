package kr.flab.movieon.account.application.payload;

public final class AccountResponse {

    private final String userId;
    private final String email;

    public AccountResponse(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "AccountResponse{" + "userId='" + userId + '\'' + ", email='" + email + '\'' + '}';
    }
}
