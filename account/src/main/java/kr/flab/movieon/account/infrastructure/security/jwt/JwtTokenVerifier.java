package kr.flab.movieon.account.infrastructure.security.jwt;

import kr.flab.movieon.account.infrastructure.security.domain.AuthInfoRepository;
import kr.flab.movieon.account.infrastructure.security.domain.TokenVerifier;
import org.springframework.stereotype.Component;

@Component
public final class JwtTokenVerifier implements TokenVerifier {

    private final AuthInfoRepository authInfoRepository;

    public JwtTokenVerifier(AuthInfoRepository authInfoRepository) {
        this.authInfoRepository = authInfoRepository;
    }

    @Override
    public boolean verify(String jti) {
        return this.authInfoRepository.existsByRefreshTokenJti(jti);
    }
}
