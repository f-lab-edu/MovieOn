package kr.flab.movieon.account.application.payload;

public class AccountTokenResponse {

    private final String accessToken;
    private final String refreshToken;

    public AccountTokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String toString() {
        return "AccountTokenResponse{" + "accessToken='" + accessToken + '\'' + ", refreshToken='"
            + refreshToken + '\'' + '}';
    }
}
