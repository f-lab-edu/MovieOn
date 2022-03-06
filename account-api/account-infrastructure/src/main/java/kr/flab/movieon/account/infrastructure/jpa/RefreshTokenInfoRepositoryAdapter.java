package kr.flab.movieon.account.infrastructure.jpa;

import kr.flab.movieon.account.infrastructure.jwt.RefreshTokenInfo;
import kr.flab.movieon.account.infrastructure.jwt.RefreshTokenInfoRepository;

public final class RefreshTokenInfoRepositoryAdapter implements RefreshTokenInfoRepository {

    private final JpaRefreshTokenInfoRepository jpaRefreshTokenInfoRepository;

    public RefreshTokenInfoRepositoryAdapter(
        JpaRefreshTokenInfoRepository jpaRefreshTokenInfoRepository) {
        this.jpaRefreshTokenInfoRepository = jpaRefreshTokenInfoRepository;
    }

    @Override
    public RefreshTokenInfo save(RefreshTokenInfo entity) {
        return jpaRefreshTokenInfoRepository.save(entity);
    }

    @Override
    public boolean existsByRefreshTokenJti(String jti) {
        return jpaRefreshTokenInfoRepository.existsByRefreshTokenJti(jti);
    }

    @Override
    public RefreshTokenInfo findByRefreshTokenJti(String jti) {
        return jpaRefreshTokenInfoRepository.findByRefreshTokenJti(jti);
    }
}
