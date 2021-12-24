package kr.flab.movieon.integrate.security.jwt;

import kr.flab.movieon.integrate.security.domain.RefreshTokenInfoRepository;
import kr.flab.movieon.integrate.security.domain.TokenVerifier;
import kr.flab.movieon.integrate.security.exception.InvalidTokenException;
import org.springframework.stereotype.Component;

@Component
public final class JwtTokenVerifier implements TokenVerifier {

    private final RefreshTokenInfoRepository refreshTokenInfoRepository;

    public JwtTokenVerifier(RefreshTokenInfoRepository refreshTokenInfoRepository) {
        this.refreshTokenInfoRepository = refreshTokenInfoRepository;
    }

    @Override
    public boolean verify(String jti) {
        if (!this.refreshTokenInfoRepository.existsByRefreshTokenJti(jti)) {
            return false;
        }
        var authEntity = this.refreshTokenInfoRepository.findByRefreshTokenJti(jti)
            .orElseThrow(InvalidTokenException::new);

        return !authEntity.isExpired();
    }
}
