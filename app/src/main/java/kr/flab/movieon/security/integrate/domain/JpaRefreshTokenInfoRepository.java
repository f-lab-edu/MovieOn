package kr.flab.movieon.security.integrate.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshTokenInfoRepository extends RefreshTokenInfoRepository,
    JpaRepository<RefreshTokenInfo, Long> {

}
