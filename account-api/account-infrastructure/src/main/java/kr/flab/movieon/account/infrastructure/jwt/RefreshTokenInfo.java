package kr.flab.movieon.account.infrastructure.jwt;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class RefreshTokenInfo {

    protected RefreshTokenInfo() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refreshTokenJti;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private boolean expired;

    public RefreshTokenInfo(String refreshTokenJti) {
        this.refreshTokenJti = refreshTokenJti;
    }

    public void expire() {
        if (this.expired) {
            throw new TokenExpiredException();
        }
        this.expired = true;
    }

    public Long getId() {
        return id;
    }

    public String getRefreshTokenJti() {
        return refreshTokenJti;
    }

    public boolean isExpired() {
        return expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefreshTokenInfo that = (RefreshTokenInfo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
