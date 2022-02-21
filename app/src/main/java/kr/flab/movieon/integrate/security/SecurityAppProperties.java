package kr.flab.movieon.integrate.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.security.jwt")
public class SecurityAppProperties {

    public static final String AUTHORIZATION = "Authorization";

    private Integer tokenExpirationSec;
    private String tokenIssuer;
    private String base64TokenSigningKey;
    private Integer refreshExpirationSec;

    protected SecurityAppProperties() {
    }

    public SecurityAppProperties(Integer tokenExpirationSec, String tokenIssuer,
        String base64TokenSigningKey, Integer refreshExpirationSec) {
        this.tokenExpirationSec = tokenExpirationSec;
        this.tokenIssuer = tokenIssuer;
        this.base64TokenSigningKey = base64TokenSigningKey;
        this.refreshExpirationSec = refreshExpirationSec;
    }

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
