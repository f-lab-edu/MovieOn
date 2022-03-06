package kr.flab.movieon.account.infrastructure.jwt;

public interface RefreshTokenInfoRepository {

    RefreshTokenInfo save(RefreshTokenInfo entity);

    boolean existsByRefreshTokenJti(String jti);

    RefreshTokenInfo findByRefreshTokenJti(String jti);
}
