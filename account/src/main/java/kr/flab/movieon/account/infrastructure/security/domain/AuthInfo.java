package kr.flab.movieon.account.infrastructure.security.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refreshTokenJti;

    @CreationTimestamp
    private LocalDateTime createdDate;

    public AuthInfo(String refreshTokenJti) {
        this.refreshTokenJti = refreshTokenJti;
        this.createdDate = LocalDateTime.now();
    }
}
