package kr.flab.movieon.account.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import kr.flab.movieon.account.domain.event.RegisteredAccountConfirmEvent;
import kr.flab.movieon.common.Role;
import kr.flab.movieon.common.domain.model.AbstractAggregateRoot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "accounts", uniqueConstraints = {
    @UniqueConstraint(columnNames = "userId"),
    @UniqueConstraint(columnNames = "email")})
public class Account extends AbstractAggregateRoot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String email;

    private String password;

    private boolean emailVerified = false;

    private String emailValidationToken;

    private LocalDateTime emailValidationTokenCreatedDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    private boolean deleted;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    private Account(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
        this.roles.add(Role.USER);
    }

    public static Account create(String userId, String email, String password) {
        var account = new Account(userId, email, password);
        account.generateEmailValidationToken();

        return account;
    }

    private void generateEmailValidationToken() {
        this.emailValidationToken = UUID.randomUUID().toString();
        this.emailValidationTokenCreatedDate = LocalDateTime.now();

        this.registerEvent(new RegisteredAccountConfirmEvent(this));
    }

    public void confirmRegister() {
        this.emailVerified = true;
    }

    public boolean isValidEmailToken(String token) {
        return this.emailValidationToken.equals(token);
    }
}
