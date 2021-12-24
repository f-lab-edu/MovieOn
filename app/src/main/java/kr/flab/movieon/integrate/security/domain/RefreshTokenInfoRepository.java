package kr.flab.movieon.integrate.security.domain;

import java.util.Optional;

public interface RefreshTokenInfoRepository {

    boolean existsByRefreshTokenJti(String refreshTokenJti);

    Optional<RefreshTokenInfo> findByRefreshTokenJti(String refreshTokenJti);

    RefreshTokenInfo save(RefreshTokenInfo entity);

    void delete(RefreshTokenInfo entity);
}
