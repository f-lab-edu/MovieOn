package kr.flab.movieon.account.infrastructure.jwt.fixtures;

import kr.flab.movieon.account.infrastructure.jwt.TokenExtractor;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenExtractor;
import org.javaunit.autoparams.customization.Customizer;
import org.javaunit.autoparams.generator.ObjectContainer;
import org.javaunit.autoparams.generator.ObjectGenerationContext;
import org.javaunit.autoparams.generator.ObjectGenerator;

public final class TokenExtractorCustomizer implements Customizer {

    @Override
    public ObjectGenerator customize(
        ObjectGenerator generator) {
        return (query, context) -> query.getType().equals(TokenExtractor.class)
            ? new ObjectContainer(factory(context))
            : generator.generate(query, context);
    }

    private TokenExtractor factory(ObjectGenerationContext context) {
        return new JwtTokenExtractor();
    }
}
