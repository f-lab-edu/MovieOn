package kr.flab.movieon.account.domain;

public final class Tokens {

    private final Token accessToken;
    private final Token refreshToken;

    public Tokens(Token accessToken, Token refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Token getAccessToken() {
        return accessToken;
    }

    public Token getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String toString() {
        return "Tokens{" + "accessToken=" + accessToken + ", refreshToken=" + refreshToken + '}';
    }
}
