package kr.flab.movieon.account.domain;

import lombok.Getter;

@Getter
public final class Tokens {

    private final Token accessToken;
    private final Token refreshToken;

    public Tokens(Token accessToken, Token refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
