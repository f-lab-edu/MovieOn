package kr.flab.movieon.account.infrastructure.jwt.fixtures;

import org.javaunit.autoparams.customization.CompositeCustomizer;

public final class JwtCustomization extends CompositeCustomizer {

    public JwtCustomization() {
        super(
            new RefreshTokenInfoRepositoryCustomizer(),
            new TokenPropertiesCustomizer(),
            new TokenExtractorCustomizer()
        );
    }
}
