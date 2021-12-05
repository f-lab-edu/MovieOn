package kr.flab.movieon.account.infrastructure.security.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {

    boolean existsByRefreshTokenJti(String refreshTokenJti);
}
