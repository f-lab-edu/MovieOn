package kr.flab.movieon.integrate.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
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
}
