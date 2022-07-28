package kr.flab.movieon.account.infrastructure.jwt.fixtures;

import kr.flab.movieon.account.infrastructure.jwt.TokenProperties;

public final class Fixtures {

    public static TokenProperties tokenProperties() {
        var tokenProperties = new TokenProperties();
        tokenProperties.setTokenIssuer("movieOn");
        tokenProperties.setBase64TokenSigningKey(
            "c2VjcmV0S2V5LXRlc3QtYXV0aG9yaXphdGlvbi1qd3QtbWFuYWdlLXRva2Vu");
        tokenProperties.setTokenExpirationSec(6000);
        tokenProperties.setRefreshExpirationSec(12000);
        return tokenProperties;
    }
}
