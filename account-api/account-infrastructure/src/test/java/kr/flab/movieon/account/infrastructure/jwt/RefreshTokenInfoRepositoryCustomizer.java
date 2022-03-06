package kr.flab.movieon.account.infrastructure.jwt;

import org.javaunit.autoparams.customization.Customizer;
import org.javaunit.autoparams.generator.ObjectContainer;
import org.javaunit.autoparams.generator.ObjectGenerationContext;
import org.javaunit.autoparams.generator.ObjectGenerator;

public final class RefreshTokenInfoRepositoryCustomizer implements Customizer {

    @Override
    public ObjectGenerator customize(
        ObjectGenerator generator) {
        return (query, context) -> query.getType().equals(RefreshTokenInfoRepository.class)
            ? new ObjectContainer(factory(context))
            : generator.generate(query, context);
    }

    private RefreshTokenInfoRepository factory(ObjectGenerationContext context) {
        return new FakeRefreshTokenInfoRepository();
    }
}
