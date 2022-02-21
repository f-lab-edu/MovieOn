package kr.flab.movieon.account.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "accounts", uniqueConstraints = {
    @UniqueConstraint(columnNames = "userId"),
    @UniqueConstraint(columnNames = "email")})
public class Account extends AbstractAggregateRoot {

    protected Account() {

    }

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

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public String getEmailValidationToken() {
        return emailValidationToken;
    }

    public LocalDateTime getEmailValidationTokenCreatedDate() {
        return emailValidationTokenCreatedDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
