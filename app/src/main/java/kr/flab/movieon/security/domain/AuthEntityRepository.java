package kr.flab.movieon.security.domain;

import java.util.Optional;

public interface AuthEntityRepository {

    boolean existsByRefreshTokenJti(String refreshTokenJti);

    Optional<AuthEntity> findByRefreshTokenJti(String refreshTokenJti);

    AuthEntity save(AuthEntity entity);

    void delete(AuthEntity entity);
}
