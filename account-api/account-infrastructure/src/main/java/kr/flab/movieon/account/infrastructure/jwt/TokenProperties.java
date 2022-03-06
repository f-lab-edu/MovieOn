package kr.flab.movieon.account.infrastructure.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.security.jwt")
public final class TokenProperties {

    private Integer tokenExpirationSec;
    private String tokenIssuer;
    private String base64TokenSigningKey;
    private Integer refreshExpirationSec;

    public Integer getTokenExpirationSec() {
        return tokenExpirationSec;
    }

    public void setTokenExpirationSec(Integer tokenExpirationSec) {
        this.tokenExpirationSec = tokenExpirationSec;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    public String getBase64TokenSigningKey() {
        return base64TokenSigningKey;
    }

    public void setBase64TokenSigningKey(String base64TokenSigningKey) {
        this.base64TokenSigningKey = base64TokenSigningKey;
    }

    public Integer getRefreshExpirationSec() {
        return refreshExpirationSec;
    }

    public void setRefreshExpirationSec(Integer refreshExpirationSec) {
        this.refreshExpirationSec = refreshExpirationSec;
    }
}
