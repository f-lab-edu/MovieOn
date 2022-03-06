package kr.flab.movieon.account.infrastructure.jpa;

import kr.flab.movieon.account.infrastructure.jwt.RefreshTokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshTokenInfoRepository extends JpaRepository<RefreshTokenInfo, Long> {

    boolean existsByRefreshTokenJti(String jti);

    RefreshTokenInfo findByRefreshTokenJti(String jti);
}
