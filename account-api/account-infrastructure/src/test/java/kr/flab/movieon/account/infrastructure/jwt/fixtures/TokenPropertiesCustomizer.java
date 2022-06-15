package kr.flab.movieon.account.infrastructure.jwt.fixtures;

import kr.flab.movieon.account.infrastructure.jwt.TokenProperties;
import org.javaunit.autoparams.customization.Customizer;
import org.javaunit.autoparams.generator.ObjectContainer;
import org.javaunit.autoparams.generator.ObjectGenerationContext;
import org.javaunit.autoparams.generator.ObjectGenerator;

public final class TokenPropertiesCustomizer implements Customizer {

    public ObjectGenerator customize(ObjectGenerator generator) {
        return (query, context) -> query.getType().equals(TokenProperties.class)
            ? new ObjectContainer(factory(context))
            : generator.generate(query, context);
    }

    private TokenProperties factory(ObjectGenerationContext context) {
        var tokenProperties = new TokenProperties();
        tokenProperties.setTokenIssuer("movieOn");
        tokenProperties.setBase64TokenSigningKey(
            "c2VjcmV0S2V5LXRlc3QtYXV0aG9yaXphdGlvbi1qd3QtbWFuYWdlLXRva2Vu");
        tokenProperties.setTokenExpirationSec(6000);
        tokenProperties.setRefreshExpirationSec(12000);
        return tokenProperties;
    }

}
