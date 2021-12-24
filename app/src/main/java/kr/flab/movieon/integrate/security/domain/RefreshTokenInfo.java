package kr.flab.movieon.integrate.security.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refreshTokenJti;
    private boolean isExpired = false;

    @CreationTimestamp
    private LocalDateTime createdDate;

    public RefreshTokenInfo(String refreshTokenJti) {
        this.refreshTokenJti = refreshTokenJti;
        this.createdDate = LocalDateTime.now();
    }

    public void expire() {
        isExpired = true;
    }
}
