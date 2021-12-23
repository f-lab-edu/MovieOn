package kr.flab.movieon.security.jwt;

import kr.flab.movieon.security.domain.AuthEntityRepository;
import kr.flab.movieon.security.domain.TokenVerifier;
import org.springframework.stereotype.Component;

@Component
public final class JwtTokenVerifier implements TokenVerifier {

    private final AuthEntityRepository authEntityRepository;

    public JwtTokenVerifier(AuthEntityRepository authEntityRepository) {
        this.authEntityRepository = authEntityRepository;
    }

    @Override
    public boolean verify(String jti) {
        return this.authEntityRepository.existsByRefreshTokenJti(jti);
    }
}
