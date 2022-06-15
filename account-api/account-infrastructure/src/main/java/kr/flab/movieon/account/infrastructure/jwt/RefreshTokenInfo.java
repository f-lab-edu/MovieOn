package kr.flab.movieon.account.infrastructure.jwt;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "REFRESH_TOKEN_INFOS")
public class RefreshTokenInfo {

    protected RefreshTokenInfo() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String refreshTokenJti;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private boolean expired;

    public RefreshTokenInfo(String refreshTokenJti) {
        this.refreshTokenJti = refreshTokenJti;
    }

    public void expire() {
        if (this.expired) {
            throw new AlreadyTokenExpiredException();
        }
        this.expired = true;
    }

    public String getRefreshTokenJti() {
        return refreshTokenJti;
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
