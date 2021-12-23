package kr.flab.movieon.security.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuthEntityRepository extends AuthEntityRepository,
    JpaRepository<AuthEntity, Long> {

}
