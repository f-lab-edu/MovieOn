package kr.flab.movieon.account.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.security.jwt")
public class SecurityAppProperties {

    private Integer tokenExpirationSec;
    private String tokenIssuer;
    private String base64TokenSigningKey;
    private Integer refreshExpirationSec;
}
