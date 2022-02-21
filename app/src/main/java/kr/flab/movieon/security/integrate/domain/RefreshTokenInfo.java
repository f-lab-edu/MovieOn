package kr.flab.movieon.security.integrate.domain;

import java.time.LocalDateTime;
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

    public Long getId() {
        return id;
    }

    public String getRefreshTokenJti() {
        return refreshTokenJti;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
