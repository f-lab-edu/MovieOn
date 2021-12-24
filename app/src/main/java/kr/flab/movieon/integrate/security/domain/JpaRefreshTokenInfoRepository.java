package kr.flab.movieon.integrate.security.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshTokenInfoRepository extends RefreshTokenInfoRepository,
    JpaRepository<RefreshTokenInfo, Long> {

}
